//package com.cangwang.process;
//
//import com.cangwang.utils.Logger;
//import com.squareup.javapoet.ClassName;
//
//import org.apache.commons.collections4.CollectionUtils;
//
//import java.io.IOException;
//import java.util.Set;
//
//import javax.annotation.processing.Filer;
//import javax.lang.model.element.Element;
//import javax.lang.model.element.TypeElement;
//import javax.lang.model.type.TypeMirror;
//import javax.lang.model.util.Elements;
//import javax.lang.model.util.Types;
//
///**
// * Make info class to Jar
// * Created by cangwang on 2017/12/22.
// */
//
//public class InjectJarProcessor {
//    public static void parseModulesGroup(Set<? extends Element> modulesElements, Logger logger, Filer mFiler, Elements elements,Types types) throws IOException {
//        if (CollectionUtils.isNotEmpty(modulesElements)) {
//            for (Element e:modulesElements){
//                TypeElement typeElement = (TypeElement)e;
//                ClassName name = ClassName.get(typeElement);
//                String path = name.packageName()+"."+name.simpleName();  //真实模块入口地址 包名+类名
//                logger.info("inject path = "+path);
////                TypeMirror typeMirror = (((TypeElement)e).getSuperclass());
////                TypeElement tE = (TypeElement) types.asElement(typeMirror);
////                ClassName nameex = ClassName.get(tE);
////                String pathex = nameex.packageName()+"."+nameex.simpleName();  //真实模块入口地址 包名+类名
////                logger.info("inject path = "+pathex);
//                TypeMirror typeMirror;
//                while (true){
//                    typeMirror = typeElement.getSuperclass();
//                    TypeElement tE = (TypeElement) types.asElement(typeMirror);
//                    ClassName nameex = ClassName.get(tE);
//                    if (nameex.simpleName().equals("Object")) break;
//                    String pathex = nameex.packageName()+"."+nameex.simpleName();  //真实模块入口地址 包名+类名
//                    logger.info("inject path = "+pathex);
//                    typeElement = (TypeElement) types.asElement(typeMirror);
//                }
//            }
//        }
//    }
//}
