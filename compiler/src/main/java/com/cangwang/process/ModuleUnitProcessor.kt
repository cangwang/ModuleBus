package com.cangwang.process

import com.cangwang.annotation.ModuleUnit
import com.cangwang.model.ICWModule
import com.cangwang.model.ModuleMeta
import com.cangwang.utils.Logger
import com.cangwang.utils.ModuleUtil
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeSpec

import org.apache.commons.collections4.CollectionUtils
import java.io.IOException
import java.util.HashMap
import javax.annotation.processing.Filer

import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements


/**
 * 解析ModuleUnit注解
 * Created by cangwang on 2017/8/30.
 */
object ModuleUnitProcessor {

    @Throws(IOException::class)
    fun parseModules(modulesElements: Set<Element>, logger: Logger, mFiler: Filer, elements: Elements): JsonArray? {
        if (CollectionUtils.isNotEmpty(modulesElements)) {
            logger.info(">>> Found moduleUnit, size is " + modulesElements.size + " <<<")
            parseModuleFile(modulesElements, logger, mFiler, elements)
            val array = JsonArray()
            for (element in modulesElements) {
                val moduleUnit = element.getAnnotation<ModuleUnit>(ModuleUnit::class.java)
                val name = ClassName.get(element as TypeElement)
                val path = name.packageName() + "." + name.simpleName()  //真实模块入口地址 包名+类名
                val jsonObject = JsonObject()
                jsonObject.addProperty("path", path)
                jsonObject.addProperty("templet", moduleUnit.templet)
                jsonObject.addProperty("title", name.simpleName())
                jsonObject.addProperty("layoutLevel", moduleUnit.layoutlevel.value)
                jsonObject.addProperty("extraLevel", moduleUnit.extralevel)
                array.add(jsonObject)
            }
            logger.info(array.toString())
            return array
        }
        return null
    }

    @Throws(IOException::class)
    fun parseModuleFile(modulesElements: Set<Element>, logger: Logger, mFiler: Filer, elements: Elements) {
        //添加loadInto方法
        val loadIntoMethodOfRootBuilder = MethodSpec.methodBuilder(ModuleUtil.METHOD_GET_MODULE)
                .returns(ICWModule::class.java!!)
                .addAnnotation(Override::class.java!!)
                .addModifiers(Modifier.PUBLIC)

        for (element in modulesElements) {
            val name = ClassName.get(element as TypeElement)
            loadIntoMethodOfRootBuilder.addStatement("return new \$T()", element)

            //构造java文件
            JavaFile.builder(ModuleUtil.FACADE_PACKAGE,
                    TypeSpec.classBuilder(ModuleUtil.NAME_OF_MODULEUNIT + name.simpleName())
                            .addJavadoc(ModuleUtil.WARNING_TIPS)
                            .addSuperinterface(ClassName.get(elements.getTypeElement(ModuleUtil.IMODULE_PROXY)))
                            .addModifiers(Modifier.PUBLIC)
                            .addMethod(loadIntoMethodOfRootBuilder.build())
                            .build()
            ).build().writeTo(mFiler)
        }
    }
}
