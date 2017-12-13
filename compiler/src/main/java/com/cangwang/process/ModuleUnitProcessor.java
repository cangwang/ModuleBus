package com.cangwang.process;

import com.cangwang.annotation.ModuleUnit;
import com.cangwang.utils.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.javapoet.ClassName;

import org.apache.commons.collections4.CollectionUtils;
import java.io.IOException;
import java.util.Set;
import javax.annotation.processing.Filer;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;


/**
 * 解析ModuleUnit注解
 * Created by cangwang on 2017/8/30.
 */
public class ModuleUnitProcessor {

    public static JsonArray parseModules(Set<? extends Element> modulesElements,Logger logger,Filer mFiler,Elements elements) throws IOException {
        if (CollectionUtils.isNotEmpty(modulesElements)){
            logger.info(">>> Found moduleUnit, size is " + modulesElements.size() + " <<<");
            JsonArray array = new JsonArray();
            for (Element element:modulesElements) {
                ModuleUnit moduleUnit=element.getAnnotation(ModuleUnit.class);
                ClassName name = ClassName.get(((TypeElement)element));
                String path = name.packageName()+"."+name.simpleName();  //真实模块入口地址 包名+类名
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("path",path);
                jsonObject.addProperty("templet",moduleUnit.templet());
                jsonObject.addProperty("title",moduleUnit.title());
                jsonObject.addProperty("layoutLevel",moduleUnit.layoutlevel().getValue());
                jsonObject.addProperty("extraLevel",moduleUnit.extralevel());
                array.add(jsonObject);
            }
            logger.info(array.toString());
            return array;
        }
        return null;
    }

}
