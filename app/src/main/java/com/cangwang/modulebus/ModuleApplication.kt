package com.cangwang.modulebus

import android.app.Application
import android.content.Context
import com.cangwang.core.ModuleBus

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
    }

    companion object {
        private const val TAG = "ModuleApplication"
    }
}