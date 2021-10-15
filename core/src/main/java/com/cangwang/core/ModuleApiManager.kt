package com.cangwang.core

import java.util.HashMap

/**
 *
 * Created by cangwang on 2018/1/7.
 */
class ModuleApiManager private constructor() {
    var aMap: HashMap<Class<out MBaseApi?>?, MBaseApi?> = HashMap()
    fun containsApi(clazz: Class<out MBaseApi?>?): Boolean {
        return aMap.containsKey(clazz)
    }

    fun <T : MBaseApi?> getApi(clazz: Class<T>?): T? {
        return aMap[clazz] as T?
    }

    fun putApi(key: Class<out MBaseApi?>?, value: MBaseApi?) {
        aMap[key] = value
    }

    fun removeApi(key: Class<out MBaseApi?>?) {
        aMap.remove(key)
    }

    companion object {
        @kotlin.jvm.JvmStatic
        var instance = ModuleApiManager()
    }
}