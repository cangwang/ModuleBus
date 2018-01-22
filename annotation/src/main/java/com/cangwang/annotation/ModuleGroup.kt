package com.cangwang.annotation

/**
 *
 * Created by cangwang on 2017/8/31.
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class ModuleGroup(vararg val value: ModuleUnit)
