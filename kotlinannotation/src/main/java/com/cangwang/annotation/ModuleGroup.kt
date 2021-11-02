package com.cangwang.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 *
 * Created by cangwang on 2017/8/31.
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(RetentionPolicy.SOURCE)
annotation class ModuleGroup(vararg val value: ModuleUnit)