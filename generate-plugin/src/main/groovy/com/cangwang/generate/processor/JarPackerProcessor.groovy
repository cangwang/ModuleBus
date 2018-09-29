package com.cangwang.generate.processor

import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.cangwang.generate.transform.AppGenerateTransform
import com.cangwang.generate.util.GenerateUtil
import com.cangwang.generate.transform.LibraryGenerateTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by cangwang on 2018/9/13.
 */
class JarPackerProcessor implements Plugin<Project> {
    Project project

    @Override
    void apply(Project project) {
        this.project = project
        if (!GenerateUtil.isAndroidPlugin(project)) {
            throw new RuntimeException("The android or android-library plugin must be applied to the project.")
        }
//        project.android.registerTransform(new GenerateTransform(project))
//        project.afterEvaluate {
            LibraryExtension library = project.extensions.findByType(LibraryExtension)
            if (library != null) {
                library.registerTransform(new LibraryGenerateTransform(library, project))
            }
            AppExtension app = project.extensions.findByType(AppExtension)
            if (app != null) {
                app.registerTransform(new AppGenerateTransform(app, project))
            }
//        }
//        project.afterEvaluate {
//            app.applicationVariants.all { variant ->
//                inject(project, variant, null)
//            }
//        }

    }

//    void inject(Project project,def variant,Map<String,String> arguments){
//        project.getGradle().getTaskGraph().addTaskExecutionGraphListener(new TaskExecutionGraphListener() {
//            @Override
//            void graphPopulated(TaskExecutionGraph taskExecutionGraph) {
//                for (Task task: taskExecutionGraph.getAllTasks()) {
//                    if (task.getProject() == project && task instanceof TransformTask
//                            && task.name.toLowerCase().contains(variant.name.toLowerCase())
//                            && task instanceof LibraryAarJarsTransform){
//                        LibraryGenerateTransform hookTransform = new LibraryGenerateTransform(project,task.transform,variant)
//                        Field field = TransformTask.class.getDeclaredField("transform")
//                        field.setAccessible(true)
//                        field.set(task,hookTransform)
//                    }
//                }
//            }
//        })
//    }
}