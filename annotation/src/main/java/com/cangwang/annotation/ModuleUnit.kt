package com.cangwang.annotation

import com.cangwang.enums.LayoutLevel

/**
 * Module单元注解
 * Created by cangwang on 2017/8/31.
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class ModuleUnit(val templet: String = "normal", val title: String = "CangWang", val layoutlevel: LayoutLevel = LayoutLevel.NORMAL, val extralevel: Int = 0)
