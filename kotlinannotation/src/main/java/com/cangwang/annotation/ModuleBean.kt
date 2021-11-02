package com.cangwang.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Module
 * Created by cangwang on
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(RetentionPolicy.CLASS)
annotation class ModuleBean 