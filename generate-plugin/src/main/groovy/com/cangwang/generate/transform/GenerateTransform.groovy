package com.cangwang.generate.transform

import com.android.build.api.transform.Context
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import com.cangwang.generate.util.GenerateUtil
import com.cangwang.generate.info.CtInfo
import org.apache.commons.io.FileUtils
import org.gradle.api.Project


/**遍历合成器
 * Created by cangwang on 2018/9/13.
 */
class GenerateTransform extends Transform{
    def MainAddress = ".gradle/modulebus/main/"
    def OutputAddress = ".gradle/modulebus/outputs/"

    Project project
    def infoSet = [] as Set<CtInfo>

    GenerateTransform(Project project) {    // 构造函数，我们将Project保存下来备用
        this.project = project
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        def startTime = System.currentTimeMillis()

        File main = project.rootProject.file(MainAddress)
        if (!main.exists())
            main.mkdirs()

        File outputs = project.rootProject.file(OutputAddress)
        if (!outputs.exists())
            outputs.mkdirs()
        //以后需要添加过滤
        inputs.each {
            TransformInput input ->
                //先遍历jar
                try {
                    input.jarInputs.each {
//                        infoSet.addAll(GenerateUtil.getNeedFromJar(it.file.getAbsolutePath(),it.file,"com",project))
                        GenerateUtil.getNeedFromJar(it.file.getAbsolutePath(),it.file,"com",project)

//                        String outputFileName = it.name.replace(".jar", "") + '-' + it.file.path.hashCode()
//                        def output = outputProvider.getContentLocation(outputFileName, it.contentTypes, it.scopes, Format.JAR)
//                        FileUtils.copyFile(it.file, output)
                    }
                } catch (Exception e) {
                    project.logger.err e.getMessage()
                }
                //对类型为“文件夹”的input进行遍历
                input.directoryInputs.each { /*DirectoryInput directoryInput ->*/
                    //文件夹里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等
//                    infoSet.addAll(GenerateUtil.getNeed(it.file.getAbsolutePath(),"com",project))
                    GenerateUtil.getNeed(it.file.getAbsolutePath(),"com",project)
                    // 获取output目录
//                    def dest = outputProvider.getContentLocation(directoryInput.name,
//                            directoryInput.contentTypes, directoryInput.scopes,
//                            Format.DIRECTORY)
//
//                    // 将input的目录复制到output指定目录
//                    FileUtils.copyDirectory(directoryInput.file, dest)

                }

                for (CtInfo info : infoSet){
                    if (info.packageName!=null){
                        String address = info.packageName.replace(".","/")
                        File addressDir = project.rootProject.file(MainAddress+address)
                        if (!addressDir.exists())
                            addressDir.mkdirs()
                        def target = new File(addressDir,info.clazz.name)
                        FileUtils.copyFile(info.path,target)
                    }
                }

                if(project.plugins.findPlugin("com.android.application") //判断是Application module
                        && main.listFiles().length > 0 ){  //判断文件夹里面不为空
                    GenerateUtil.generateReleaseJar()
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