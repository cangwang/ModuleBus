package com.cangwang.process

import com.cangwang.annotation.ModuleUnit
import com.cangwang.model.ICWModule
import com.cangwang.utils.Logger
import com.cangwang.utils.ModuleUtil
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.kotlinpoet.*
import javax.annotation.processing.Filer
import javax.lang.model.util.Elements
import org.apache.commons.collections4.CollectionUtils
import java.io.IOException
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

/**
 * 解析ModuleUnit注解
 * Created by cangwang on 2017/8/30.
 */
object ModuleUnitProcessor {
    @Throws(IOException::class)
    fun parseModules(modulesElements: Set<Element?>, logger: Logger, mFiler: Filer?, elements: Elements): JsonArray? {
        if (CollectionUtils.isNotEmpty(modulesElements)) {
            logger.info(">>> Found moduleUnit, size is " + modulesElements.size + " <<<")
            parseModuleFile(modulesElements, logger, mFiler, elements)
            val array = JsonArray()
            for (element in modulesElements) {
                val moduleUnit = element!!.getAnnotation(ModuleUnit::class.java)
                val name: ClassName = (element as TypeElement).asClassName()
                val path: String = name.packageName.toString() + "." + name.simpleName //真实模块入口地址 包名+类名
                val jsonObject = JsonObject()
                jsonObject.addProperty("path", path)
                jsonObject.addProperty("templet", moduleUnit.templet)
                jsonObject.addProperty("title", name.simpleName)
                jsonObject.addProperty("layoutLevel", moduleUnit.layoutlevel.value)
                jsonObject.addProperty("inflateLevel", moduleUnit.inflateLevel)
                jsonObject.addProperty("extraLevel", moduleUnit.extralevel)
                array.add(jsonObject)
            }
            logger.info(array.toString())
            return array
        }
        return null
    }

    @JvmStatic
    @Throws(IOException::class)
    fun parseModuleFile(modulesElements: Set<Element?>, logger: Logger?, mFiler: Filer?, elements: Elements) {
        //添加loadInto方法
        val loadIntoMethodOfRootBuilder = FunSpec.builder(ModuleUtil.METHOD_GET_MODULE)
                .returns(ICWModule::class.java)
                .addModifiers(KModifier.PUBLIC, KModifier.OVERRIDE)
        for (element in modulesElements) {
            val name: ClassName = (element as TypeElement).asClassName()
            loadIntoMethodOfRootBuilder.addStatement("return %T()", element as TypeElement)

            //构造java文件
            FileSpec.builder(ModuleUtil.FACADE_PACKAGE,ModuleUtil.NAME_OF_MODULEUNIT + name.simpleName)
                    .addType(TypeSpec.classBuilder(ModuleUtil.NAME_OF_MODULEUNIT + name.simpleName)
                            .addKdoc(ModuleUtil.WARNING_TIPS)
                            .addSuperinterface((elements.getTypeElement(ModuleUtil.IMODULE_PROXY)).asClassName())
                            .addModifiers(KModifier.PUBLIC)
                            .addFunction(loadIntoMethodOfRootBuilder.build())
                            .build()
                    ).build().writeTo(mFiler!!)
        }
    }
}