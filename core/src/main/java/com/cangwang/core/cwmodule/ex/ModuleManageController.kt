package com.cangwang.core.cwmodule.ex

import android.os.Bundle
import android.util.Log
import com.cangwang.core.ModuleBus
import com.cangwang.core.ModuleCenter
import com.cangwang.core.cwmodule.CWModuleContext
import com.cangwang.core.cwmodule.api.BackPressStack

class ModuleManageController(val moduleManager: ModuleExManager, val moduleContext: CWModuleContext) {

    fun initView(TAG: String) {
        val moduleNames = ModuleBus.instance?.getModuleList(moduleManager.template)
        if (ModuleCenter.isFromNetWork && moduleNames != null && moduleNames.isNotEmpty()) {  //在线加载
            for (moduleName in moduleNames) {
                moduleManager.getPool().execute {
                    val module = CWModuleExFactory.newModuleInstance(moduleName)
                    if (module != null) {
                        moduleManager.getHandler().post {
                            val before = System.currentTimeMillis()
                            module.onCreate(moduleContext, null)
                            Log.d(TAG, "modulename: " + moduleName + " init time = " + (System.currentTimeMillis() - before) + "ms")
                            moduleManager.putModule(moduleName, module)
                        }
                    }
                }
            }
        } else {   //本地缓存加载
            var module: CWAbsExModule
            val moduleList = CWModuleExFactory.instance?.getTempleList(moduleManager.template)
            if (moduleList == null || moduleList.isEmpty()) return
            for (moduleIn in moduleList) {
                if (moduleIn is CWAbsExModule) {
                    module = moduleIn
                    val before = System.currentTimeMillis()
                    module.onCreate(moduleContext, null)
                    Log.d(TAG, "modulename: " + moduleIn.javaClass.canonicalName + " init time = " + (System.currentTimeMillis() - before) + "ms")
                    moduleManager.putModule(moduleIn.javaClass.canonicalName, module)
                }
            }
        }
    }

    fun onBackPressed() {
        if (moduleManager.onBackPressed()) {
            return
        }
        if (BackPressStack.stack.size > 0) return
    }

    fun removeModule(moduleName: String?) {
        if (moduleName != null && moduleName.isNotEmpty()) {
            val module = moduleManager.getModuleByNames(moduleName)
            if (module != null) {
                module.detachView() //先移除界面，再销毁
                module.onDestroy()
                moduleManager.remove(moduleName)
            }
        }
    }

    fun addModule(moduleName: String?, extend: Bundle?, listener: ModuleLoadListener?) {
        if (moduleName != null && moduleName.isNotEmpty()) {
            if (moduleManager.allModules.containsKey(moduleName)) //模块不重复添加
                return
            var module = moduleManager.getModuleByNames(moduleName)
            if (module == null) {
                module = CWModuleExFactory.newModuleInstance(moduleName)
            }
            if (module != null) {
                val result = module.onCreate(moduleContext, extend)
                listener?.laodResult(result) //监听回调
                if (result) moduleManager.putModule(moduleName, module)
            }
        }
    }

    fun moduleVisible(moduleName: String?, isVisible: Boolean) {
        if (moduleName != null && moduleName.isNotEmpty()) {
            val module = moduleManager.getModuleByNames(moduleName)
            module?.setVisible(isVisible)
        }
    }
}