package com.cangwang.process;

import com.cangwang.annotation.ModuleUnit;
import com.cangwang.model.ICWModule;
import com.cangwang.model.ModuleMeta;
import com.cangwang.utils.Logger;
import com.cangwang.utils.ModuleUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import org.apache.commons.collections4.CollectionUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Filer;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
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
            parseModuleFile(modulesElements,logger,mFiler,elements);
            JsonArray array = new JsonArray();
            for (Element element:modulesElements) {
                ModuleUnit moduleUnit=element.getAnnotation(ModuleUnit.class);
                ClassName name = ClassName.get(((TypeElement)element));
                String path = name.packageName()+"."+name.simpleName();  //真实模块入口地址 包名+类名
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("path",path);
                jsonObject.addProperty("templet",moduleUnit.templet());
                jsonObject.addProperty("title",name.simpleName());
                jsonObject.addProperty("layoutLevel",moduleUnit.layoutlevel().getValue());
                jsonObject.addProperty("extraLevel",moduleUnit.extralevel());
                array.add(jsonObject);
            }
            logger.info(array.toString());
            return array;
        }
        return null;
    }

    public static void parseModuleFile(Set<? extends Element> modulesElements,Logger logger,Filer mFiler,Elements elements) throws IOException{
        //添加loadInto方法
        MethodSpec.Builder loadIntoMethodOfRootBuilder = MethodSpec.methodBuilder(ModuleUtil.METHOD_GET_MODULE)
                .returns(ICWModule.class)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC);

        for (Element element:modulesElements){
            ClassName name = ClassName.get(((TypeElement)element));

            loadIntoMethodOfRootBuilder.addStatement("return new $T()", (TypeElement)element);

            //构造java文件
            JavaFile.builder(ModuleUtil.FACADE_PACKAGE,
                    TypeSpec.classBuilder(ModuleUtil.NAME_OF_MODULEUNIT+name.simpleName())
                            .addJavadoc(ModuleUtil.WARNING_TIPS)
                            .addSuperinterface(ClassName.get(elements.getTypeElement(ModuleUtil.IMODULE_PROXY)))
                            .addModifiers(Modifier.PUBLIC)
                            .addMethod(loadIntoMethodOfRootBuilder.build())
                            .build()
            ).build().writeTo(mFiler);
        }
    }
}
