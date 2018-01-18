package com.cangwang.annotation

import com.cangwang.enums.LayoutLevel
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Module单元注解
 * Created by cangwang on 2017/8/31.
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(RetentionPolicy.CLASS)
annotation class ModuleUnit(val templet: String = "normal", val title: String = "CangWang", val layoutlevel: LayoutLevel = LayoutLevel.NORMAL, val extralevel: Int = 0)
