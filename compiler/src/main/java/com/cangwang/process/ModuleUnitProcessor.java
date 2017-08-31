package com.cangwang.process;

import com.cangwang.annotation.ModuleGroup;
import com.cangwang.annotation.ModuleUnit;
import com.cangwang.utils.Logger;
import com.cangwang.utils.ModuleUtil;
import com.google.auto.service.AutoService;

import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

/**
 * 注解
 * Created by cangwang on 2017/8/30.
 */
@AutoService(Process.class)
public class ModuleUnitProcessor extends AbstractProcessor {

    private Logger logger;
    private Elements elements;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        logger = new Logger(processingEnv.getMessager());   // Package the log utils.
        elements = processingEnv.getElementUtils();      // Get class meta.
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if(CollectionUtils.isNotEmpty(set)){
            Set<? extends Element> modulesElements = roundEnvironment.getElementsAnnotatedWith(ModuleGroup.class);
            try {
                logger.info(">>> Found moduleGroup, start... <<<");
                parseModulesGroup(modulesElements);

            } catch (Exception e) {
                logger.error(e);
            }
        }
        return false;
    }

    private void parseModulesGroup(Set<? extends Element> modulesElements) throws IOException {
        if (CollectionUtils.isNotEmpty(modulesElements)){
            logger.info(">>> Found moduleGroup, size is " + modulesElements.size() + " <<<");

            // Interface of ARouter
//            TypeElement type_IRouteGroup = elements.getTypeElement(IModule_GROUP);
        }
    }
}
