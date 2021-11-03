package com.cangwang.core.cwmodule.ex

import com.cangwang.core.cwmodule.CWModuleContext
import android.os.Bundle
import com.cangwang.core.R
import com.cangwang.core.ModuleBus
import androidx.collection.SparseArrayCompat
import com.cangwang.core.ModuleEvent
import com.cangwang.core.IBaseClient
import androidx.appcompat.app.AppCompatActivity
import android.content.res.Configuration
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
    private var moduleManagerController = ModuleManageController(moduleManager, moduleContext)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.module_rank_layout)
        ModuleBus.instance?.register(this)
        rootLayout = findViewById<View>(R.id.root_layout) as ViewGroup
        mTopViewGroup = findViewById<View>(R.id.layout_top) as ViewGroup
        mBottomViewGroup = findViewById<View>(R.id.layout_bottom) as ViewGroup
        pluginViewGroup = findViewById<View>(R.id.layout_plugincenter) as ViewGroup
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
        moduleManagerController.initView(TAG)
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
        moduleManagerController.onBackPressed()
        super.onBackPressed()
    }

    /**
     * 添加模块
     * @param moduleName
     * @param extend
     */
    @ModuleEvent(coreClientClass = IBaseClient::class)
    fun addModule(moduleName: String?, extend: Bundle?) {
        moduleManagerController.addModule(moduleName, extend, null)
    }

    /**
     * 移除模块
     * @param moduleName
     */
    @ModuleEvent(coreClientClass = IBaseClient::class)
    fun removeModule(moduleName: String?) {
        moduleManagerController.removeModule(moduleName)
    }

    @ModuleEvent(coreClientClass = IBaseClient::class)
    fun moduleVisible(moduleName: String?, isVisible: Boolean) {
        moduleManagerController.moduleVisible(moduleName, isVisible)
    }
}