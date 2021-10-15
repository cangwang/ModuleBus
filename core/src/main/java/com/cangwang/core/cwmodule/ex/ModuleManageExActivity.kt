package com.cangwang.core.cwmodule.ex

import com.cangwang.core.cwmodule.CWModuleContext
import android.os.Bundle
import com.cangwang.core.cwmodule.api.BackPressStack
import com.cangwang.core.R
import com.cangwang.core.ModuleBus
import androidx.collection.SparseArrayCompat
import com.cangwang.core.ModuleCenter
import com.cangwang.core.ModuleEvent
import com.cangwang.core.IBaseClient
import androidx.appcompat.app.AppCompatActivity
import android.content.res.Configuration
import android.util.Log
import android.view.View
import android.view.ViewGroup

/**
 * Created by cangwang on 2017/6/15.
 */
abstract class ModuleManageExActivity : AppCompatActivity() {
    private val TAG = "ModuleManageExActivity"
    private var mTopViewGroup: ViewGroup? = null
    private var mBottomViewGroup: ViewGroup? = null
    private var pluginViewGroup: ViewGroup? = null
    private var rootLayout: ViewGroup? = null
    private var moduleManager = ModuleExManager()
    private var moduleContext =  CWModuleContext()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.module_rank_layout)
        ModuleBus.instance?.register(this)
        rootLayout = findViewById<View>(R.id.root_layout) as ViewGroup
        mTopViewGroup = findViewById<View>(R.id.layout_top) as ViewGroup
        mBottomViewGroup = findViewById<View>(R.id.layout_bottom) as ViewGroup
        pluginViewGroup = findViewById<View>(R.id.layout_plugincenter) as ViewGroup
        moduleManager = ModuleExManager()
        moduleManager.moduleConfig(moduleConfig())
        initView(savedInstanceState)
    }

    fun setBackGroundResouce(color: Int) {
        rootLayout!!.setBackgroundResource(color)
    }

    fun initView(mSavedInstanceState: Bundle?) {
        moduleContext.activity = this
        moduleContext.saveInstance = mSavedInstanceState
        //关联视图
        val sVerticalViews = SparseArrayCompat<ViewGroup?>()
        sVerticalViews.put(CWModuleContext.TOP_VIEW_GROUP, mTopViewGroup)
        sVerticalViews.put(CWModuleContext.BOTTOM_VIEW_GROUP, mBottomViewGroup)
        sVerticalViews.put(CWModuleContext.PLUGIN_CENTER_VIEW, pluginViewGroup)
        moduleContext.viewGroups = sVerticalViews
        moduleContext.templateName = moduleConfig()
        val moduleNames = ModuleBus.instance?.getModuleList(moduleManager.template)
        if (moduleNames != null && moduleNames.isNotEmpty()) {  //在线加载
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

    abstract fun moduleConfig(): String?
    override fun onResume() {
        super.onResume()
         moduleManager.onResume()
    }

    override fun onPause() {
         moduleManager.onPause()
        super.onPause()
    }

    override fun onStop() {
         moduleManager.onStop()
        super.onStop()
    }

    override fun onDestroy() {
         moduleManager.onDestroy()
        ModuleBus.instance?.unregister(this)
        super.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        moduleManager.onConfigurationChanged(newConfig)
        super.onConfigurationChanged(newConfig)
    }

    override fun onBackPressed() {
        if (moduleManager.onBackPressed()) {
            return
        }
        if (BackPressStack.stack.size > 0) return
        super.onBackPressed()
    }

    /**
     * 添加模块
     * @param moduleName
     * @param extend
     */
    @ModuleEvent(coreClientClass = IBaseClient::class)
    fun addModule(moduleName: String?, extend: Bundle?) {
        addModule(moduleName, extend, null)
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

    /**
     * 移除模块
     * @param moduleName
     */
    @ModuleEvent(coreClientClass = IBaseClient::class)
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

    @ModuleEvent(coreClientClass = IBaseClient::class)
    fun moduleVisible(moduleName: String?, isVisible: Boolean) {
        if (moduleName != null && moduleName.isNotEmpty()) {
            val module = moduleManager.getModuleByNames(moduleName)
            module?.setVisible(isVisible)
        }
    }
}