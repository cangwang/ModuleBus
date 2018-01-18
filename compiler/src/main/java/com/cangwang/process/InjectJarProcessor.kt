package com.cangwang.process

import com.cangwang.utils.Logger
import com.squareup.javapoet.ClassName

import org.apache.commons.collections4.CollectionUtils

import java.io.IOException

import javax.annotation.processing.Filer
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

/**
 * Make info class to Jar
 * Created by cangwang on 2017/12/22.
 */

object InjectJarProcessor {
    @Throws(IOException::class)
    fun parseModulesGroup(modulesElements: Set<Element>, logger: Logger, mFiler: Filer, elements: Elements, types: Types) {
        if (CollectionUtils.isNotEmpty(modulesElements)) {
            for (e in modulesElements) {
                var typeElement = e as TypeElement
                val name = ClassName.get(typeElement)
                val path = name.packageName() + "." + name.simpleName()  //真实模块入口地址 包名+类名
                logger.info("inject path = " + path)
                //                TypeMirror typeMirror = (((TypeElement)e).getSuperclass());
                //                TypeElement tE = (TypeElement) types.asElement(typeMirror);
                //                ClassName nameex = ClassName.get(tE);
                //                String pathex = nameex.packageName()+"."+nameex.simpleName();  //真实模块入口地址 包名+类名
                //                logger.info("inject path = "+pathex);
                var typeMirror: TypeMirror
                while (true) {
                    typeMirror = typeElement.superclass
                    val tE = types.asElement(typeMirror) as TypeElement
                    val nameex = ClassName.get(tE)
                    if (nameex.simpleName() == "Object") break
                    val pathex = nameex.packageName() + "." + nameex.simpleName()  //真实模块入口地址 包名+类名
                    logger.info("inject path = " + pathex)
                    typeElement = types.asElement(typeMirror) as TypeElement
                }
            }
        }
    }
}
