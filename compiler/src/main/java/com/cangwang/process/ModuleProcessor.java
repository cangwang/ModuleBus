package com.cangwang.process;

import com.cangwang.annotation.ModuleGroup;
import com.cangwang.annotation.ModuleUnit;
import com.cangwang.model.ModuleMeta;
import com.cangwang.utils.Logger;
import com.google.auto.service.AutoService;

import org.apache.commons.collections4.CollectionUtils;

import java.util.HashMap;
import java.util.LinkedHashSet;
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

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnv.getFiler();                  // Generate class.
        logger = new Logger(processingEnv.getMessager());   // Package the log utils.
        types = processingEnv.getTypeUtils();            // Get type utils.
        elements = processingEnv.getElementUtils();      // Get class meta.
        System.out.println("------------------------------");
        System.out.println("ModuleProcess init");
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
                ModuleUnitProcessor.parseModules(moduleUnitElements,logger,mFiler,elements);
                logger.info(">>> Found moduleGroup, start... <<<");
                ModuleGroupProcessor.parseModulesGroup(moduleGroupElements,logger,mFiler,elements);
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return true;
    }
}
