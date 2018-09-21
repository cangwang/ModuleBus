package com.cangwang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Module
 * Created by cangwang on
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface ModuleBean {
}
