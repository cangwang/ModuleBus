package com.cangwang.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 *
 * Created by cangwang on 2017/8/31.
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(RetentionPolicy.SOURCE)
annotation class InjectBean
