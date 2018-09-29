package com.cangwang.generate.transform

import com.android.build.api.transform.*
import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import com.google.common.collect.Sets
import org.gradle.api.Project

/**遍历合成器
 * Created by cangwang on 2018/9/13.
 */
class AppGenerateTransform extends Transform{
    def MainAddress = ".gradle/modulebus/main/"
    def OutputAddress = ".gradle/modulebus/outputs/"

    Project project
    AppExtension app

    AppGenerateTransform(AppExtension app, Project project) {    // 构造函数，我们将Project保存下来备用
        this.project = project
        this.app = app
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        def startTime = System.currentTimeMillis()

        //以后需要添加过滤
        inputs.each {
            TransformInput input ->
                input.directoryInputs.each {
//                    GenerateUtil.getNeedFromJar(it.file.getAbsolutePath(),it.file,"com",project)
                }

        }
        project.logger.error("LibraryGenerateTransform cast :" + (System.currentTimeMillis() - startTime) / 1000 + " secs")
    }

    @Override
    String getName() {
        return "___AppGenerateTransform___"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }
}