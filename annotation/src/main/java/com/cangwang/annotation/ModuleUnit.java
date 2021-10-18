package com.cangwang.annotation;

import com.cangwang.enums.LayoutLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Module单元注解
 * Created by cangwang on 2017/8/31.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface ModuleUnit {
    String templet() default "normal";
    String title() default "CangWang";
    LayoutLevel layoutlevel() default LayoutLevel.NORMAL;
    int inflateLevel() default -1;
    int extralevel() default 0;
}
