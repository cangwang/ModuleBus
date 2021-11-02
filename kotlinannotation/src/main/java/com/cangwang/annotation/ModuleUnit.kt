package com.cangwang.annotation

import com.cangwang.enums.LayoutLevel
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Module单元注解
 * Created by cangwang on 2017/8/31.
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(RetentionPolicy.CLASS)
annotation class ModuleUnit(val templet: String = "normal", val title: String = "CangWang", val layoutlevel: LayoutLevel = LayoutLevel.NORMAL, val inflateLevel: Int = -1, val extralevel: Int = 0)