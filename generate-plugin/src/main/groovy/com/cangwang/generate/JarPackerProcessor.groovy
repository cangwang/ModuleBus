package com.cangwang.generate

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
        project.android.registerTransform(new GenerateTransform(project))
    }
}