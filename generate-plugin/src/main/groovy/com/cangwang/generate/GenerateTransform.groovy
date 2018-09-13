package com.cangwang.generate

import android.util.ArraySet
import com.android.build.api.transform.Context
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils
import org.gradle.api.Project


/**遍历合成器
 * Created by cangwang on 2018/9/13.
 */
class GenerateTransform extends Transform{

    Project project
    ArraySet<CtInfo> set = ArraySet()

    GenerateTransform(Project project) {    // 构造函数，我们将Project保存下来备用
        this.project = project
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        def startTime = System.currentTimeMillis()
        inputs.each {
            TransformInput input ->
                //先遍历jar
                try {
                    input.jarInputs.each {
                        set.addAll(GenerateUtil.getNeed(it.file.getAbsolutePath(),"com",project))

//                        String outputFileName = it.name.replace(".jar", "") + '-' + it.file.path.hashCode()
//                        def output = outputProvider.getContentLocation(outputFileName, it.contentTypes, it.scopes, Format.JAR)
                        FileUtils.copyFile(it.file, output)
                    }
                } catch (Exception e) {
                    project.logger.err e.getMessage()
                }

                //对类型为“文件夹”的input进行遍历
                input.directoryInputs.each { DirectoryInput directoryInput ->
                    //文件夹里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等
                    set.addAll(GenerateUtil.getNeed(it.file.getAbsolutePath(),"com",project))
                    // 获取output目录
                    def dest = outputProvider.getContentLocation(directoryInput.name,
                            directoryInput.contentTypes, directoryInput.scopes,
                            Format.DIRECTORY)

                    // 将input的目录复制到output指定目录
                    FileUtils.copyDirectory(directoryInput.file, dest)
                }
        }
        project.logger.error("GenerateTransform cast :" + (System.currentTimeMillis() - startTime) / 1000 + " secs")
    }

    @Override
    String getName() {
        return "___GenerateTransform___"
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