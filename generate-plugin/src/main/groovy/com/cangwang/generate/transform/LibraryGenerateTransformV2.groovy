package com.cangwang.generate.transform

import com.android.build.api.transform.*
import com.android.build.gradle.LibraryExtension
import com.cangwang.generate.util.GenerateUtil
import org.gradle.api.Project

/**遍历合成器
 * Created by cangwang on 2018/9/13.
 */
class LibraryGenerateTransformV2 extends Transform{
    def MainAddress = ".gradle/modulebus/main/"
    def OutputAddress = ".gradle/modulebus/outputs/"

    Project project
    LibraryExtension library
    Transform transform
    def variant

    LibraryGenerateTransformV2(Project project, Transform transform, def variant) {    // 构造函数，我们将Project保存下来备用
        this.project = project
        this.transform =transform
        this.variant = variant
    }

//    @Override
//    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
//        def startTime = System.currentTimeMillis()
//
//        //以后需要添加过滤
//        inputs.each {
//            TransformInput input ->
//                input.directoryInputs.each {
//                    GenerateUtil.getNeedFromJar(it.file.getAbsolutePath(),it.file,"com",project)
//                }
//
//        }
//        project.logger.error("LibraryGenerateTransform cast :" + (System.currentTimeMillis() - startTime) / 1000 + " secs")
//    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {

        def startTime = System.currentTimeMillis()


        //以后需要添加过滤
        transformInvocation.getReferencedInputs().each {
            TransformInput input ->
                input.jarInputs.each {
                    GenerateUtil.getNeedFromJar(it.file.getAbsolutePath(),it.file,"com",project)
                }

                input.directoryInputs.each {

                }
        }
        project.logger.error("LibraryGenerateTransform cast :" + (System.currentTimeMillis() - startTime) / 1000 + " secs")
        transform.transform(transformInvocation)
    }

    @Override
    String getName() {
        return "___LibraryGenerateTransform___"
    }

//    @Override
//    public String getName() {
//        return transform.getName()
//    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return transform.getInputTypes()
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return transform.getScopes()
    }

    @Override
    Collection<SecondaryFile> getSecondaryFiles() {
        return transform.getSecondaryFiles()
    }

    @Override
    Collection<File> getSecondaryFileOutputs() {
        return transform.getSecondaryFileOutputs()
    }

    @Override
    Set<? super QualifiedContent.Scope> getReferencedScopes() {
        return transform.getReferencedScopes()
    }

    @Override
    Collection<File> getSecondaryDirectoryOutputs() {
        return transform.getSecondaryDirectoryOutputs()
    }

    @Override
    Collection<File> getSecondaryFileInputs() {
        return transform.getSecondaryFileInputs()
    }

    @Override
    Map<String, Object> getParameterInputs() {
        return transform.getParameterInputs()
    }

    @Override
    Set<QualifiedContent.ContentType> getOutputTypes() {
        return transform.getOutputTypes()
    }


    @Override
    boolean isIncremental() {
        return transform.isIncremental()
    }

    @Override
    boolean isCacheable() {
        return transform.isCacheable()
    }
}