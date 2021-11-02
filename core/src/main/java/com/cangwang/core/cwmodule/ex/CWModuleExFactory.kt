package com.cangwang.core.cwmodule.ex

import com.cangwang.model.IModuleFactory

/**
 * Created by cangwang on 2017/6/16.
 */
object CWModuleExFactory {
    private const val FACTORY_PATH = "com.cangwang.core.ModuleCenterFactory"
    var instance: IModuleFactory? = null
        get() = if (field == null) {
            try {
                val factoryClazz = Class.forName(FACTORY_PATH) as Class<out IModuleFactory>
                if (factoryClazz != null) {
                    field = factoryClazz.newInstance()
                    field
                } else {
                    null
                }
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
                null
            } catch (e: InstantiationException) {
                e.printStackTrace()
                null
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
                null
            }
        } else field
        private set

    fun newModuleInstance(name: String?): CWAbsExModule? {
        if (name == null || name == "") {
            return null
        }
        try {
            val moduleClzz = Class.forName(name) as Class<out CWAbsExModule>
            return if (moduleClzz != null) {
                moduleClzz.newInstance() as CWAbsExModule
            } else null
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return null
    }
}