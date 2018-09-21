package com.cangwang.generate

import groovyjarjarasm.asm.ClassVisitor
import javassist.ClassPool
import javassist.CtClass
import org.gradle.api.Project
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassReader

import java.util.jar.JarEntry
import java.util.jar.JarFile


/**
 * Created by cangwang on 2018/9/13.
 */
class GenerateUtil {
    private final static ClassPool pool = ClassPool.getDefault()
    private final static String IMODULE_BEAN = "com.cangwang.core.bean.IModuleBean"
    private final static String IMODULE_ANNOTATION = "com.cangwang.annotation.ModuleBean"
    def jarSet = [] as Set<CtInfo>

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
        def set = [] as Set<CtInfo>
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
                            } else if (c.annotations != null){
                                for (CtClass an :c.annotations){
                                    if (an.packageName+"."+an.name == IMODULE_ANNOTATION){
                                        set.add(CtInfo(an.packageName,filePath))
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

    static Set<CtInfo> getNeedFromJar(String path, File file,String packageName, Project project) {
        pool.appendClassPath(path)
        //project.android.bootClasspath 加入android.jar，否则找不到android相关的所有类
        pool.appendClassPath(project.android.bootClasspath[0].toString())
        def set = [] as Set<CtInfo>
        JarFile jarFile = new JarFile(file)
        Enumeration<JarEntry> entries = jarFile.entries()
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement()
            String filename = entry.getName()
            if (filterClass(filename)) continue

            InputStream stream = jarFile.getInputStream(entry)
            if (stream != null) {
                ClassReader c = new ClassReader(stream.bytes)
                if (c.interfaces != null && c.interfaces.size() > 0){
                    for (String inter :c.interfaces){
                        if (c.packageName+"."+inter == IMODULE_BEAN){
                            FileOutputStream outputStream = new FileOutputStream(GenerateTransform.MainAddress+"/"+c.className)
                            outputStream.write(stream)
                        }
                    }
                }

                ClassVisitor cv =  ClassVisitor()
                c.accept(cv,0)
                AnnotationVisitor av = cv.visitAnnotation(IMODULE_ANNOTATION,false)
                if (av!= null ){
                    FileOutputStream outputStream = new FileOutputStream(GenerateTransform.MainAddress+"/"+c.className)
                    outputStream.write(stream)
                }
                stream.close()
            }
        }
        return set
    }

    public static boolean filterClass(String filename) {
        if (!filename.endsWith(".class")
                || filename.contains('R$')
                || filename.contains('R.class')
                || filename.contains("BuildConfig.class")) {
            return true
        }

        return false
    }

    static String getClassName(int index, String filePath) {
        int end = filePath.length() - 6 // .class = 6
        return filePath.substring(index, end).replace('\\', '.').replace('/', '.')
    }

    private static File generateReleaseJar(File classesDir, def argFiles, def classPath, def target, def source) {
        def classpathSeparator = ";"
        if (!System.properties['os.name'].toLowerCase().contains('windows')) {
            classpathSeparator = ":"
        }
        def p
        if (classPath.size() == 0) {  //解压
            p = ("javac -encoding UTF-8 -target " + target + " -source " + source + " -d . " + argFiles.join(' ')).execute(null, classesDir)
        } else {
            p = ("javac -encoding UTF-8 -target " + target + " -source " + source + " -d . -classpath " + classPath.join(classpathSeparator) + " " + argFiles.join(' ')).execute(null, classesDir)
        }

        def result = p.waitFor()
        if (result != 0) {
            throw new RuntimeException("Failure to convert java source to bytecode: \n" + p.err.text)
        }

        p = "jar cvf outputs/classes.jar -C classes . ".execute(null, classesDir.parentFile)  //读取java文件
        result = p.waitFor()
        p.destroy()
        p = null
        if (result != 0) {
            throw new RuntimeException("failure to package classes.jar: \n" + p.err.text)
        }

        return new File(classesDir.parentFile, 'outputs/classes.jar')  //返回classes.jar文件
    }
}