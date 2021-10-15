package com.cangwang.core.cwmodule.ex

import com.cangwang.core.cwmodule.api.BackPressStack
import android.os.Looper
import android.content.res.Configuration
import android.os.Handler
import android.util.Log
import androidx.collection.ArrayMap
import com.cangwang.core.util.ModuleUtil
import java.util.ArrayList
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by cangwang on 2016/12/26.
 */
class ModuleExManager {
    var moduleNames: List<String> = ArrayList() //模块名字
        private set
    var allModules = ArrayMap<String?, CWAbsExModule>() //模块实体
    var template: String? = null
        private set

    fun moduleConfig(modules: List<String>) {
        moduleNames = modules
    }

    fun moduleConfig(template: String?) {
        this.template = template
    }

    private var handler: Handler? = null
    private var pool: ExecutorService? = null

    fun getHandler(): Handler {
        if (handler == null) {
            handler = Handler(Looper.getMainLooper())
        }
        return handler!!
    }

    fun getPool(): ExecutorService {
        if (pool == null) {
            pool = Executors.newSingleThreadExecutor()
        }
        return pool!!
    }

    fun getModuleByNames(name: String?): CWAbsExModule? {
        return if (!ModuleUtil.empty(allModules)) allModules[name] else null
    }

    fun remove(name: String?) {
        if (!ModuleUtil.empty(allModules)) {
            allModules.remove(name)
        }
    }

    fun putModule(name: String?, module: CWAbsExModule) {
        allModules[name] = module
    }

    fun onResume() {
        for (module in allModules.values) module?.onResume()
    }

    fun onPause() {
        for (module in allModules.values) module?.onPause()
    }

    fun onStop() {
        for (module in allModules.values) module?.onStop()
    }

    fun onConfigurationChanged(newConfig: Configuration) {
        for (module in allModules.values) module?.onOrientationChanges(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
    }

    fun onDestroy() {
        handler = null
        pool = null
        for (module in allModules.values) {
            module?.onDestroy()
        }
    }

    fun onBackPressed(): Boolean {
        var hasCallback = false
        //        for (CWAbsExModule module:allModules.values()) {
//            if (module != null) {
//                boolean isCall=module.onBackPress();
//                if (isCall)
//                    hasCallback =true;
//            }
//        }
        if (BackPressStack.stack.size > 0) {
            Log.e("ModuleExManager", "peek " + BackPressStack.stack.toString())
            hasCallback = BackPressStack.stack.peek().onBackPress()
        }
        return hasCallback
    }
}