package com.cangwang.generate.util

import com.cangwang.generate.transform.GenerateTransform
import javassist.ClassPool
import javassist.CtClass
import org.gradle.api.Project
import org.gradle.internal.impldep.org.testng.log4testng.Logger
import org.objectweb.asm.ClassReader

import java.lang.annotation.Annotation
import java.util.jar.JarEntry
import java.util.jar.JarFile

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

    private final static ModuleBeanAnnotationVisitor moduleBeanAnnotationVisitor = new ModuleBeanAnnotationVisitor()

    static void getNeedFromJar(String path, File file,String packageName, Project project) {
        pool.appendClassPath(path)
        //project.android.bootClasspath 加入android.jar，否则找不到android相关的所有类
        pool.appendClassPath(project.android.bootClasspath[0].toString())
        JarFile jarFile = new JarFile(file)
        Enumeration<JarEntry> entries = jarFile.entries()

//        File tempJar = new File(GenerateTransform.MainAddress+"/"+jarFile.name+".jar")
//        JarOutputStream jarOutputStream
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement()
            String filename = entry.getName()
            if (filterClass(filename)) continue

            InputStream stream = jarFile.getInputStream(entry)
            if (stream != null) {
                ClassReader c = new ClassReader(stream.bytes)
//                if (c.interfaces != null && c.interfaces.size() > 0){
//                    for (String inter :c.interfaces){
//                        if (c.packageName+"."+inter == IMODULE_BEAN){
//                            FileOutputStream outputStream = new FileOutputStream(GenerateTransform.MainAddress+"/"+c.className)
//                            outputStream.write(stream)
//                        }
//                    }
//                }

                c.accept(moduleBeanAnnotationVisitor,0)
                if (moduleBeanAnnotationVisitor.isNeedCopy()){
                    FileOutputStream outputStream = new FileOutputStream(GenerateTransform.MainAddress+"/"+filename)
                    outputStream.write(stream)
                    outputStream.close()

//                    if (!tempJar.exists())
//                        tempJar.createNewFile()
//                    if (jarOutputStream ==null){
//                        jarOutputStream = new JarOutputStream(new FileOutputStream(tempJar))
//                    }else {
//                        ZipEntry zipEntry = new ZipEntry(filename)
//                        jarOutputStream.putNextEntry(zipEntry)
//                        jarOutputStream.write(stream.bytes)
//                    }
                    Logger.i('-- add [' + filename + '] to new ')
                }
                stream.close()
            }
//            if (jarOutputStream !=null){
//                jarOutputStream.close()
//            }
        }
    }

    static void getNeedFromDirectory(String path, File dir,String packageName, Project project){
        pool.appendClassPath(path)
        //project.android.bootClasspath 加入android.jar，否则找不到android相关的所有类
        pool.appendClassPath(project.android.bootClasspath[0].toString());

        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->
                if (!filterClass(file.absolutePath)) {
                    String filePath = file.absolutePath//确保当前文件是class文件，并且不是系统自动生成的class文件
                    // 判断当前目录是否是在我们的应用包里面
                    int index = filePath.indexOf(packageName);
                    boolean isMyPackage = index != -1;
                    if (isMyPackage) {
                        String className = Utils.getClassName(index, filePath);
                        CtClass c = pool.getCtClass(className)
                        if (c.isFrozen()) c.defrost()

                        for (Annotation an:c.getAnnotations()) {
                            if (an.annotationType().canonicalName == IMODULE_ANNOTATION){

                            }
                        }

                        for (CtClass inter:c.getInterfaces()){
                            if (inter.name.contains(IMODULE_BEAN)){

                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean filterClass(String filename) {
        if (!filename.endsWith(".class")
                || filename.contains('R$')
                || filename.contains('R.class')
                || filename.contains("BuildConfig.class")
                || !filename.startsWith("android")
                || !filename.startsWith("com.android")
                || !filename.startsWith("kotlin")) {
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

//    static def copyInfo = ArrayList()
//    static void addCopyInfo(CopyInfo info){
//        copyInfo.add(info)
//    }
//
//    static List<CopyInfo> getCopyInfos(){
//        return copyInfo
//    }
}