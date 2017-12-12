package com.cangwang.process;

import com.cangwang.annotation.ModuleGroup;
import com.cangwang.annotation.ModuleUnit;
import com.cangwang.utils.Logger;
import com.cangwang.utils.ModuleUtil;
import com.google.auto.service.AutoService;
import com.google.gson.JsonArray;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
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
            if (applicationName ==null) {
                moduleName = options.get("moduleName");
                System.out.println("moduleName = " +moduleName);
            }else {
                System.out.println("applicationName = " +applicationName);
                if (applicationName != null ) {
                    String path = System.getProperty("user.dir") +"/"+ applicationName + "/src/main/assets/center.json";
                    logger.info(path);
                    try {
                        List<String> moduleNameList = ModuleUtil.readSetting();
                        JsonArray jsonArray = new JsonArray();
                        for (String name:moduleNameList){
                            logger.info(name);
                            String json = ModuleUtil.readJsonFile( System.getProperty("user.dir") +"/"+ name + "/src/main/assets/center.json");
                            if (json.isEmpty()) continue;
                            jsonArray.addAll(ModuleUtil.parserJsonArray(json));
                        }
                        ModuleUtil.createCenterJson(applicationName);
                        ModuleUtil.writeJsonFile(path, jsonArray.toString());
                        ModuleUtil.deleteRootCenter();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
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
                        ModuleUtil.createCenterJson(moduleName);
                        ModuleUtil.writeJsonFile(System.getProperty("user.dir") + "/" + moduleName + "/src/main/assets/center.json", moduleArrary.toString());
                    }
                    logger.info(moduleArrary.toString());
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }

        return true;
    }
}
