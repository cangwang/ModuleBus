package com.cangwang.core.cwmodule.ex

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.util.SparseArrayCompat
import android.util.Log
import android.view.ViewGroup

import com.cangwang.core.IBaseClient
import com.cangwang.core.ModuleBus
import com.cangwang.core.ModuleCenter
import com.cangwang.core.ModuleEvent
import com.cangwang.core.R
import com.cangwang.core.cwmodule.CWModuleContext
import com.cangwang.model.ICWModule

/**
 * Created by cangwang on 2017/6/15.
 */

abstract class ModuleManageActivity : Activity() {
    private val TAG = "ModuleManageExActivity"
    private var mTopViewGroup: ViewGroup? = null
    private var mBottomViewGroup: ViewGroup? = null
    private var pluginViewGroup: ViewGroup? = null

    private var moduleManager: ModuleExManager? = null
    private var moduleContext: CWModuleContext? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.module_rank_layout)
        ModuleBus.getInstance().register(this)
        mTopViewGroup = findViewById<View>(R.id.layout_top)
        mBottomViewGroup = findViewById<View>(R.id.layout_bottom)
        pluginViewGroup = findViewById<View>(R.id.layout_plugincenter)
        moduleManager = ModuleExManager()
        moduleManager!!.moduleConfig(moduleConfig())
        initView(savedInstanceState)
    }

    fun initView(mSavedInstanceState: Bundle?) {
        moduleContext = CWModuleContext()
        moduleContext!!.activity = this
        moduleContext!!.saveInstance = mSavedInstanceState
        //关联视图
        val sVerticalViews = SparseArrayCompat<ViewGroup>()
        sVerticalViews.put(CWModuleContext.TOP_VIEW_GROUP, mTopViewGroup)
        sVerticalViews.put(CWModuleContext.BOTTOM_VIEW_GROUP, mBottomViewGroup)
        sVerticalViews.put(CWModuleContext.PLUGIN_CENTER_VIEW, pluginViewGroup)
        moduleContext!!.viewGroups = sVerticalViews

        if (ModuleCenter.isFromNetWork) {  //在线加载
            for (moduleName in ModuleBus.getInstance().getModuleList(moduleManager!!.template)!!) {
                moduleManager!!.pool.execute {
                    val module = CWModuleExFactory.newModuleInstance(moduleName)
                    if (module != null) {
                        moduleManager!!.handler.post {
                            val before = System.currentTimeMillis()
                            module.init(moduleContext, null)
                            Log.d(TAG, "modulename: " + moduleName + " init time = " + (System.currentTimeMillis() - before) + "ms")
                            moduleManager!!.putModule(moduleName, module)
                        }
                    }
                }
            }
        } else {   //本地缓存加载
            var module: CWAbsExModule
            val moduleList = CWModuleExFactory.getInstance()!!.getTempleList(moduleManager!!.template)
            if (moduleList == null || moduleList.isEmpty()) return
            for (moduleIn in moduleList) {
                module = moduleIn as CWAbsExModule
                val before = System.currentTimeMillis()
                module.init(moduleContext, null)
                Log.d(TAG, "modulename: " + moduleIn.javaClass.getCanonicalName() + " init time = " + (System.currentTimeMillis() - before) + "ms")
                moduleManager!!.putModule(moduleIn.javaClass.getCanonicalName(), module)
            }
        }
    }


    abstract fun moduleConfig(): String

    override fun onResume() {
        super.onResume()
        moduleManager!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        moduleManager!!.onPause()
    }

    override fun onStop() {
        super.onStop()
        moduleManager!!.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        moduleManager!!.onDestroy()
        ModuleBus.getInstance().unregister(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        moduleManager!!.onConfigurationChanged(newConfig)
    }

    /**
     * 添加模块
     * @param moduleName
     * @param extend
     */
    @ModuleEvent(coreClientClass = IBaseClient::class)
    fun addModule(moduleName: String, extend: Bundle) {
        addModule(moduleName, extend, null)
    }

    fun addModule(moduleName: String?, extend: Bundle, listener: ModuleLoadListener?) {
        if (moduleName != null && !moduleName.isEmpty()) {
            if (moduleManager!!.allModules.containsKey(moduleName))
            //模块不重复添加
                return
            var module = moduleManager!!.getModuleByNames(moduleName)
            if (module == null) {
                module = CWModuleExFactory.newModuleInstance(moduleName)
            }
            if (moduleContext != null && module != null) {
                val result = module.init(moduleContext, extend)
                listener?.laodResult(result)  //监听回调
                if (result)
                    moduleManager!!.putModule(moduleName, module)
            }
        }
    }

    /**
     * 移除模块
     * @param moduleName
     */
    @ModuleEvent(coreClientClass = IBaseClient::class)
    fun removeModule(moduleName: String?) {
        if (moduleName != null && !moduleName.isEmpty()) {
            val module = moduleManager!!.getModuleByNames(moduleName)
            if (module != null) {
                module.detachView()  //先移除界面，再销毁
                module.onDestroy()
                moduleManager!!.remove(moduleName)
            }
        }
    }

    @ModuleEvent(coreClientClass = IBaseClient::class)
    fun moduleVisible(moduleName: String?, isVisible: Boolean) {
        if (moduleName != null && !moduleName.isEmpty()) {
            val module = moduleManager!!.getModuleByNames(moduleName)
            module?.setVisible(isVisible)
        }
    }
}
