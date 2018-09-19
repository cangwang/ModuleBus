package com.cangwang.generate

import android.util.ArraySet
import javassist.ClassPool
import javassist.CtClass
import org.gradle.api.Project


/**
 * Created by cangwang on 2018/9/13.
 */
class GenerateUtil {
    private final static ClassPool pool = ClassPool.getDefault()
    private final static String IMODULE_BEAN = "com.cangwang.core.bean.IModuleBean"
    private final static String IMODULE_ANNOTATION = "com.cangwang.annotation.ModuleBean"

    static boolean isAndroidPlugin(Project project) {
        if (project.plugins.findPlugin("com.android.application") || project.plugins.findPlugin("android") ||
                project.plugins.findPlugin("com.android.test")) {
            return true
        } else if (project.plugins.findPlugin("com.android.library") || project.plugins.findPlugin("android-library")) {
            return true
        } else {
            return false
        }
    }

    /**
     * 判断是否带有标记的接口或者注解
     * @param path
     * @param packageName
     * @param project
     * @return
     */
    static Set<CtInfo> getNeed(String path, String packageName, Project project) {
        pool.appendClassPath(path)
        //project.android.bootClasspath 加入android.jar，否则找不到android相关的所有类
        pool.appendClassPath(project.android.bootClasspath[0].toString())
        ArraySet<CtInfo> set = ArraySet()
        File dir = new File(path)
        if (dir.isDirectory()){
            dir.eachFileRecurse {
                File file ->
                   String filePath = file.absolutePath //确保当前文件是class文件，并且不是系统自动生成的class文件
                    if (filePath.endsWith(".class") && !filePath.contains('R$') && !filePath.contains('$')//代理类
                            && !filePath.contains('R.class') && !filePath.contains("BuildConfig.class")){
                        int index = filePath.indexOf(packageName)
                        // 判断当前目录是否是在我们的应用包里面
                        boolean isMyPackage = index!=-1
                        if (isMyPackage){
                            String className = getClassName(index,filePath)
                            CtClass c= pool.getCtClass(className)
                            if (c.interfaces != null){
                                for (CtClass inter :c.interfaces){
                                    if (inter.packageName+"."+inter.name == IMODULE_BEAN){
                                        set.add(CtInfo(inter,inter.packageName,filePath))
                                    }
                                }
                            }
                            if (c.annotations != null){
                                for (CtClass an :c.annotations){
                                    if (an.packageName+"."+an.name == IMODULE_ANNOTATION){
                                        set.add(CtInfo(inter.packageName,filePath))
                                    }
                                }
                            }
//                            c.fields.each {
//                                CtField field->
//                                    field.declaringClass
//                            }
                        }
                    }
            }
        }
        return set
    }

    static String getClassName(int index, String filePath) {
        int end = filePath.length() - 6 // .class = 6
        return filePath.substring(index, end).replace('\\', '.').replace('/', '.')
    }
}