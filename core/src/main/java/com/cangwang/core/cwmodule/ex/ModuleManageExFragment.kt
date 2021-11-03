package com.cangwang.core.cwmodule.ex

import com.cangwang.core.cwmodule.CWModuleContext
import android.os.Bundle
import android.view.LayoutInflater
import com.cangwang.core.R
import com.cangwang.core.ModuleBus
import androidx.collection.SparseArrayCompat
import com.cangwang.core.ModuleEvent
import com.cangwang.core.IBaseClient
import android.content.res.Configuration
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Created by cangwang on 2017/6/15.
 */
abstract class ModuleManageExFragment : Fragment() {
    private val TAG = "ModuleManageExFragment"
    private var mTopViewGroup: ViewGroup? = null
    private var mBottomViewGroup: ViewGroup? = null
    private var pluginViewGroup: ViewGroup? = null
    private var rootView: View? = null
    private var moduleManager = ModuleExManager()
    private var moduleContext = CWModuleContext()
    private var moduleManagerController = ModuleManageController(moduleManager, moduleContext)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.module_rank_layout, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTopViewGroup = rootView!!.findViewById<View>(R.id.layout_top) as ViewGroup
        mBottomViewGroup = rootView!!.findViewById<View>(R.id.layout_bottom) as ViewGroup
        pluginViewGroup = rootView!!.findViewById<View>(R.id.layout_plugincenter) as ViewGroup
        moduleManager.moduleConfig(moduleConfig())
        ModuleBus.instance?.register(this)
        moduleContext.activity = activity
        moduleContext.saveInstance = savedInstanceState
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

    override fun onStop() {
        super.onStop()
        moduleManager.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        ModuleBus.instance?.unregister(this)
        moduleManager.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        moduleManager.onConfigurationChanged(newConfig)
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