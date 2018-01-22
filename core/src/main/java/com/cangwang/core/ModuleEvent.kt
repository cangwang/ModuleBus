package com.cangwang.core

import java.lang.annotation.Documented

import kotlin.reflect.KClass

/**
 * Created by zjl on 16/11/17.
 */
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD)
@Documented
annotation class ModuleEvent(val coreClientClass: KClass<*>, val single: Boolean = false)
