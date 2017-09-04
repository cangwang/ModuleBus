//package com.cangwang.process;
//
//import com.cangwang.annotation.ModuleUnit;
//import com.cangwang.model.ModuleMeta;
//import com.cangwang.utils.Logger;
//import com.cangwang.utils.ModuleUtil;
//import com.google.auto.service.AutoService;
//import com.squareup.javapoet.ClassName;
//import com.squareup.javapoet.JavaFile;
//import com.squareup.javapoet.MethodSpec;
//import com.squareup.javapoet.ParameterSpec;
//import com.squareup.javapoet.ParameterizedTypeName;
//import com.squareup.javapoet.TypeSpec;
//
//import org.apache.commons.collections4.CollectionUtils;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.LinkedHashSet;
//import java.util.Map;
//import java.util.Set;
//
//import javax.annotation.processing.AbstractProcessor;
//import javax.annotation.processing.Filer;
//import javax.annotation.processing.ProcessingEnvironment;
//import javax.annotation.processing.Processor;
//import javax.annotation.processing.RoundEnvironment;
//import javax.lang.model.SourceVersion;
//import javax.lang.model.element.Element;
//import javax.lang.model.element.Modifier;
//import javax.lang.model.element.TypeElement;
//import javax.lang.model.util.Elements;
//import javax.lang.model.util.Types;
//
///**
// * Created by Administrator on 2017/9/4.
// */
//
//@AutoService(Processor.class)
//public class ModuleGroupProcessor extends AbstractProcessor {
//
//    private Map<String, ModuleMeta> groupMap = new HashMap<>(); // ModuleName and routeMeta.
//    private Filer mFiler;       // File util, write class file into disk.
//    private Logger logger;
//    private Types types;
//    private Elements elements;
////    private Messager messager;
//
//    @Override
//    public synchronized void init(ProcessingEnvironment processingEnv) {
//        super.init(processingEnv);
//        mFiler = processingEnv.getFiler();                  // Generate class.
//        logger = new Logger(processingEnv.getMessager());   // Package the log utils.
//        types = processingEnv.getTypeUtils();            // Get type utils.
//        elements = processingEnv.getElementUtils();      // Get class meta.
//        System.out.println("------------------------------");
//        System.out.println("ModuleUnit init");
//    }
//
//    @Override
//    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        if(CollectionUtils.isNotEmpty(set)){
//            Set<? extends Element> modulesElements = roundEnvironment.getElementsAnnotatedWith(ModuleUnit.class);
//            try {
//                logger.info(">>> Found moduleUnit, start... <<<");
//                parseModulesGroup(modulesElements);
//
//            } catch (Exception e) {
//                logger.error(e);
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        Set<String> annotations = new LinkedHashSet<>();
//        annotations.add(ModuleUnit.class.getCanonicalName());
//        return annotations;
//    }
//
//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.latestSupported();
//    }
//
//    private void parseModulesGroup(Set<? extends Element> modulesElements) throws IOException {
//        if (CollectionUtils.isNotEmpty(modulesElements)){
//            logger.info(">>> Found moduleUnit, size is " + modulesElements.size() + " <<<");
//
//            //ModuleMeta类
//            ClassName moduleMetaCn = ClassName.get(ModuleMeta.class);
//
//            ParameterizedTypeName inputMapTypeOfGroup = ParameterizedTypeName.get(
//                    ClassName.get(Map.class),
//                    ClassName.get(String.class),
//                    ClassName.get(ModuleMeta.class)
//            );
//
//            //groups参数
//            ParameterSpec groupParamSpec = ParameterSpec.builder(inputMapTypeOfGroup,"groups").build();
//
//            //添加loadInto方法
//            MethodSpec.Builder loadIntoMethodOfRootBuilder = MethodSpec.methodBuilder(ModuleUtil.METHOD_LOAD_INTO)
//                    .returns(void.class)
//                    .addAnnotation(Override.class)
//                    .addModifiers(Modifier.PUBLIC)
//                    .addParameter(groupParamSpec);
//
//            for (Element element:modulesElements){
//                ModuleUnit moduleUnit=element.getAnnotation(ModuleUnit.class);
//                ModuleMeta moduleMeta= new ModuleMeta(moduleUnit,element);
//                ClassName name = ClassName.get(((TypeElement)element));
//                groupMap.put(element.getSimpleName().toString(),moduleMeta);
//
//                loadIntoMethodOfRootBuilder.addStatement("groups.put($S,new $T($S,$S,$L,$L))",
//                        moduleMeta.moduleName,
//                        moduleMetaCn,
//                        name.packageName()+"."+name.simpleName(),
//                        moduleMeta.templet,
//                        moduleMeta.layoutlevel.getValue(),
//                        moduleMeta.extralevel
//                );
//
//                //构造java文件
//                JavaFile.builder(ModuleUtil.FACADE_PACKAGE,
//                        TypeSpec.classBuilder(moduleMeta.moduleName)
//                                .addJavadoc(ModuleUtil.WARNING_TIPS)
//                                .addSuperinterface(ClassName.get(elements.getTypeElement(ModuleUtil.IMODULE_GROUP)))
//                                .addModifiers(Modifier.PUBLIC)
//                                .addMethod(loadIntoMethodOfRootBuilder.build())
//                                .build()
//                ).build().writeTo(mFiler);
//            }
//        }
//    }
//}
