package com.cangwang.generate.info

import javassist.CtClass

/**
 * Created by cangwang on 2018/9/13.
 */
class CtInfo {
    String packageName
    String path
    CtClass clazz

    CtInfo(CtClass clazz, String packageName, String path) {
        this.className = clazz
        this.packageName = packageName
        this.path = path
    }
}