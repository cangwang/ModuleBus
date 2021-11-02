package com.cangwang.process

import com.cangwang.annotation.ModuleGroup
import com.cangwang.annotation.ModuleUnit
import com.cangwang.utils.Logger
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.kotlinpoet.asClassName
import org.apache.commons.collections4.CollectionUtils
import java.io.IOException
import javax.annotation.processing.Filer
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

/**
 * 解析ModuleGroup注解
 */
object ModuleGroupProcessor {
    @Throws(IOException::class)
    fun parseModulesGroup(modulesElements: Set<Element?>, logger: Logger, mFiler: Filer?, elements: Elements): JsonArray? {
        if (CollectionUtils.isNotEmpty(modulesElements)) {
            logger.info(">>> Found moduleGroup, size is " + modulesElements.size + " <<<")
            ModuleUnitProcessor.parseModuleFile(modulesElements, logger, mFiler, elements)
            for (element in modulesElements) {
                val group = element!!.getAnnotation(ModuleGroup::class.java)
                val units: Array<out ModuleUnit> = group.value
                return parseModules(units, element, logger, mFiler, elements)
            }
        }
        return null
    }

    @Throws(IOException::class)
    fun parseModules(modulesElements: Array<out ModuleUnit>, element: Element?, logger: Logger, mFiler: Filer?, elements: Elements?): JsonArray? {
        if (modulesElements.isNotEmpty()) {
            logger.info(">>> Found moduleUnit, size is " + modulesElements.size + " <<<")
            var moduleUnit: ModuleUnit
            val array = JsonArray()
            for (i in modulesElements.indices) {
                moduleUnit = modulesElements[i]
                val name = (element as TypeElement).asClassName()
                val path: String = name.packageName + "." + name.simpleName //真实模块入口地址 包名+类名
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
}