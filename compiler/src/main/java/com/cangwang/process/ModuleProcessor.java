package com.cangwang.process;

import com.cangwang.annotation.ModuleGroup;
import com.cangwang.annotation.ModuleUnit;
import com.cangwang.bean.ModuleUnitBean;
import com.cangwang.model.IModuleFactory;
import com.cangwang.utils.Logger;
import com.cangwang.utils.ModuleUtil;
import com.google.auto.service.AutoService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by cangwang on 2017/12/6.
 */
@AutoService(Processor.class)
public class ModuleProcessor extends AbstractProcessor {
    private Filer mFiler;       // File util, write class file into disk.
    private Logger logger;
    private Types types;
    private Elements elements;
    private String applicationName;
    private String moduleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        System.out.println("------------------------------");
        System.out.println("ModuleProcess init");

        mFiler = processingEnv.getFiler();                  // Generate class.
        logger = new Logger(processingEnv.getMessager());   // Package the log utils.
        ModuleUtil.logger = logger;
        types = processingEnv.getTypeUtils();            // Get type utils.
        elements = processingEnv.getElementUtils();      // Get class meta.
        Map<String, String> options = processingEnv.getOptions();
        if (MapUtils.isNotEmpty(options)) {
            applicationName = options.get("applicationName");
            if (applicationName == null) {
                moduleName = options.get("moduleName");
                System.out.println("moduleName = " +moduleName);
            }else {
                System.out.println("applicationName = " +applicationName);
                initAppJson();
            }
        }
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(ModuleUnit.class.getCanonicalName());
        annotations.add(ModuleGroup.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if(CollectionUtils.isNotEmpty(set)){
            Set<? extends Element> moduleUnitElements = roundEnvironment.getElementsAnnotatedWith(ModuleUnit.class);
            Set<? extends Element> moduleGroupElements = roundEnvironment.getElementsAnnotatedWith(ModuleGroup.class);

            try {
                //遍历注解
                logger.info(">>> Found moduleUnit, start... <<<");
                JsonArray units = ModuleUnitProcessor.parseModules(moduleUnitElements, logger, mFiler, elements);
                logger.info(">>> Found moduleGroup, start... <<<");
                JsonArray groups = ModuleGroupProcessor.parseModulesGroup(moduleGroupElements, logger, mFiler, elements);

                if (moduleName != null) {
                    JsonArray moduleArrary = new JsonArray();
                    if (units != null)
                        moduleArrary.addAll(units);
                    if (groups != null)
                        moduleArrary.addAll(groups);
                    if (moduleArrary.size() > 0) {
                        //写入每个json文件
                        ModuleUtil.createCenterJson(moduleName);
                        ModuleUtil.writeJsonFile(ModuleUtil.getJsonAddress(moduleName), moduleArrary.toString());
                    }
                    logger.info(moduleArrary.toString());
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }

        return true;
    }

    private void initAppJson(){
        if (applicationName != null ) {
            String path = ModuleUtil.getJsonAddress(applicationName);
            try {
                //先清理app center.json缓存数据
                ModuleUtil.createCenterJson(applicationName);
                //获取引用module的列表
                List<String> moduleNameList = ModuleUtil.readSetting();
                JsonArray jsonArray = new JsonArray();
                if (moduleNameList != null) {
                    for (String name : moduleNameList) {
                        //读取module center.json列表
                        String json = ModuleUtil.readJsonFile(ModuleUtil.getJsonAddress(name));
                        if (json.isEmpty()) continue;
                        jsonArray.addAll(ModuleUtil.parserJsonArray(json));
                    }
                }
                logger.info("读取"+path+" module center.json列表:"+jsonArray.toString());
                //转换为对象做类型排序
                Map<String,LinkedList<ModuleUnitBean>> map =new HashMap<>();
                for (int i = 0;i<jsonArray.size();i++){
                    JsonObject o = jsonArray.get(i).getAsJsonObject();
                    final ModuleUnitBean bean = ModuleUtil.gson.fromJson(o, ModuleUnitBean.class);
                    if (map.containsKey(bean.templet)){
                        map.get(bean.templet).add(bean);
                    }else {
                        LinkedList<ModuleUnitBean> list = new LinkedList<ModuleUnitBean>(){{add(bean);}};
                        map.put(bean.templet,list);
                    }
                }
                if (!map.isEmpty()) {
                    JsonObject o = new JsonObject();
                    for (Map.Entry<String, LinkedList<ModuleUnitBean>> entry : map.entrySet()) {
                        //排列层级
                        Collections.sort(entry.getValue());
                        o.add(entry.getKey(), ModuleUtil.listToJson(entry.getValue()));
                    }
                    //写入到center.json
                    ModuleUtil.writeJsonFile(path, o.toString());
                }
                parseCenter(null,logger,mFiler,elements);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void parseCenter(Set<? extends Element> modulesElements,Logger logger,Filer mFiler,Elements elements) throws IOException{

        logger.info("init instance");

        FieldSpec.Builder fieldInstanceBuilder = FieldSpec.builder(IModuleFactory.class, "sInstance", Modifier.PRIVATE, Modifier.STATIC);
        //添加loadInto方法
        MethodSpec.Builder getInstanceBuilder = MethodSpec.methodBuilder(ModuleUtil.METHOD_FACTROY_GET_INSTANCE)
                .returns(IModuleFactory.class)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .beginControlFlow("if (null == sInstance)")
                .addStatement("sInstance = new ModuleCenterFactory()")
                .endControlFlow()
                .addStatement("return sInstance");


        //构造java文件
        JavaFile.builder(ModuleUtil.FACADE_PACKAGE,
                TypeSpec.classBuilder("ModuleCenterFactory")
                        .addJavadoc(ModuleUtil.WARNING_TIPS)
                        .addSuperinterface(ClassName.get(elements.getTypeElement(ModuleUtil.IMODULE_FACTORY)))
                        .addModifiers(Modifier.PUBLIC)
                        .addField(fieldInstanceBuilder.build())
                        .addMethod(getInstanceBuilder.build())
                        .build()
        ).build().writeTo(mFiler);
    }

}
