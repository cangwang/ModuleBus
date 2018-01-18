package com.cangwang.core.cwmodule.ex

import com.cangwang.model.IModuleFactory

/**
 * Created by cangwang on 2017/6/16.
 */

object CWModuleExFactory {
    private val FACTORY_PATH = "com.cangwang.core.ModuleCenterFactory"

    private var instance: IModuleFactory? = null

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

    fun getInstance(): IModuleFactory? {
        if (instance == null) {
            try {
                val factoryClazz = Class.forName(FACTORY_PATH) as Class<out IModuleFactory>
                if (factoryClazz != null) {
                    instance = factoryClazz.newInstance()
                    return instance
                }
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

            return null
        } else
            return instance
    }
}
