package com.cangwang.generate.transform

import com.android.build.api.transform.*
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import com.cangwang.generate.util.GenerateUtil
import com.google.common.collect.Sets
import org.gradle.api.Project

/**遍历合成器
 * Created by cangwang on 2018/9/13.
 */
class LibraryGenerateTransform extends Transform{
    def MainAddress = ".gradle/modulebus/main/"
    def OutputAddress = ".gradle/modulebus/outputs/"

    Project project
    LibraryExtension library

    LibraryGenerateTransform(LibraryExtension library,Project project) {    // 构造函数，我们将Project保存下来备用
        this.project = project
        this.library = library
    }

//    @Override
//    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
//        def startTime = System.currentTimeMillis()
//
//        //以后需要添加过滤
//        inputs.each {
//            TransformInput input ->
//                //先遍历jar
//                try {
//                    input.jarInputs.each {
//                        GenerateUtil.getNeedFromJar(it.file.getAbsolutePath(),it.file,"com",project)
//                    }
//                } catch (Exception e) {
//                    project.logger.err e.getMessage()
//                }
//                input.directoryInputs.each {
//                    GenerateUtil.getNeedFromDirectory(it.file.getAbsolutePath(),it.file,"com",project)
//
//                }
//
//        }
//        project.logger.error("LibraryGenerateTransform cast :" + (System.currentTimeMillis() - startTime) / 1000 + " secs")
//    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        //以后需要添加过滤
        transformInvocation.inputs.each {
                //先遍历jar
            try {
                it.jarInputs.each {
                    GenerateUtil.getNeedFromJar(it.file.getAbsolutePath(),it.file,"com",project)
                }
            } catch (Exception e) {
                project.logger.err e.getMessage()
            }
            it.directoryInputs.each {
                GenerateUtil.getNeedFromDirectory(it.file.getAbsolutePath(),it.file,"com",project)

            }

        }
    }

    String  makeArrPath() {
        //获取最后一个
        library.libraryVariants.all { variant ->
            variant.outputs.each { output ->
                aarFile = output.outputFile
            }
        }
    }

    @Override
    String getName() {
        return "___LibraryGenerateTransform___"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return Sets.immutableEnumSet(
                QualifiedContent.Scope.PROJECT
        )
    }

    @Override
    boolean isIncremental() {
        return false
    }
}