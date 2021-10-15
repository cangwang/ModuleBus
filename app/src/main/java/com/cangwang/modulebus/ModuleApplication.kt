package com.cangwang.modulebus

import android.app.Application
import android.content.Context
import com.cangwang.modulebus.ExModule.PageExConfig
import android.util.Log
import com.cangwang.core.ModuleBus
import com.cangwang.core.util.ModuleImpl

/**
 * Created by cangwang on 2017/2/25.
 */
class ModuleApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        ModuleBus.init(base)
    }

    override fun onCreate() {
        super.onCreate()
        val time = System.currentTimeMillis()
        for (implName in PageExConfig.moduleCreate) {
            try {
                val clazz = Class.forName(implName)
                if (clazz.newInstance() is ModuleImpl) {
                    val impl = clazz.newInstance() as ModuleImpl
                    impl.onLoad(this)
                }
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            }
        }
        Log.v(TAG, "interface load time = " + (System.currentTimeMillis() - time))
    }

    companion object {
        private const val TAG = "ModuleApplication"
    }
}