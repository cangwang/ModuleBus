package com.cangwang.process;

import com.cangwang.annotation.ModuleUnit;
import com.cangwang.model.ModuleMeta;
import com.cangwang.utils.Logger;
import com.cangwang.utils.ModuleUtil;
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
 * 注解
 * Created by cangwang on 2017/8/30.
 */
public class ModuleUnitProcessor {

    public static void parseModules(Set<? extends Element> modulesElements,Logger logger,Filer mFiler,Elements elements) throws IOException {
        if (CollectionUtils.isNotEmpty(modulesElements)){
            logger.info(">>> Found moduleUnit, size is " + modulesElements.size() + " <<<");

            //ModuleMeta类
            ClassName moduleMetaCn = ClassName.get(ModuleMeta.class);

            ParameterizedTypeName inputMapTypeOfGroup = ParameterizedTypeName.get(
                    ClassName.get(List.class),
                    ClassName.get(ModuleMeta.class)
            );

            //groups参数
            ParameterSpec groupParamSpec = ParameterSpec.builder(inputMapTypeOfGroup,"metaSet").build();

            //添加loadInto方法
            MethodSpec.Builder loadIntoMethodOfRootBuilder = MethodSpec.methodBuilder(ModuleUtil.METHOD_LOAD_INTO)
                    .returns(void.class)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(groupParamSpec);

            for (Element element:modulesElements){
                ModuleUnit moduleUnit=element.getAnnotation(ModuleUnit.class);
                ClassName name = ClassName.get(((TypeElement)element));
                String address = name.packageName()+"."+name.simpleName();  //真实模块入口地址 包名+类名
                ModuleMeta moduleMeta= new ModuleMeta(moduleUnit,address);
                Map<String, ModuleMeta> groupMap = new HashMap<>();
                groupMap.put(element.getSimpleName().toString(),moduleMeta);

                String[] nameZone = ModuleUtil.splitDot(moduleMeta.moduleName);
                moduleMeta.title = !moduleMeta.title.equals("CangWang") ? moduleMeta.title:nameZone[ nameZone.length-1];

                String[] templets = ModuleUtil.split(moduleMeta.templet);
                for (String templet:templets) {
                    loadIntoMethodOfRootBuilder.addStatement("metaSet.add(new $T($S,$S,$S,$L,$L))",
                            moduleMetaCn,
                            templet,
                            moduleMeta.moduleName,
                            moduleMeta.title,
                            moduleMeta.layoutlevel.getValue(),
                            moduleMeta.extralevel
                    );
                }

                logger.info(">>> build moduleUnit,moduleMeta = " + moduleMeta.toString() + " <<<");

                //构造java文件
                JavaFile.builder(ModuleUtil.FACADE_PACKAGE,
                        TypeSpec.classBuilder(ModuleUtil.NAME_OF_MODULEUNIT+name.simpleName())
                                .addJavadoc(ModuleUtil.WARNING_TIPS)
                                .addSuperinterface(ClassName.get(elements.getTypeElement(ModuleUtil.IMODULE_UNIT)))
                                .addModifiers(Modifier.PUBLIC)
                                .addMethod(loadIntoMethodOfRootBuilder.build())
                                .build()
                ).build().writeTo(mFiler);
            }
        }
    }

}
