package com.cangwang.core.cwmodule.ex

import com.cangwang.core.cwmodule.CWModuleContext
import android.os.Bundle
import android.view.LayoutInflater
import com.cangwang.core.R
import com.cangwang.core.ModuleBus
import androidx.collection.SparseArrayCompat
import com.cangwang.core.ModuleCenter
import com.cangwang.core.ModuleEvent
import com.cangwang.core.IBaseClient
import android.content.res.Configuration
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Created by cangwang on 2017/6/15.
 */
abstract class ModuleManageFragment : Fragment() {
    private val TAG = "ModuleManageExFragment"
    private var mTopViewGroup: ViewGroup? = null
    private var mBottomViewGroup: ViewGroup? = null
    private var pluginViewGroup: ViewGroup? = null
    private var rootView: View? = null
    private var moduleManager =  ModuleExManager()
    private var moduleContext = CWModuleContext()
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
        sVerticalViews.put(CWModuleContext.Companion.TOP_VIEW_GROUP, mTopViewGroup)
        sVerticalViews.put(CWModuleContext.Companion.BOTTOM_VIEW_GROUP, mBottomViewGroup)
        sVerticalViews.put(CWModuleContext.Companion.PLUGIN_CENTER_VIEW, pluginViewGroup)
        moduleContext.viewGroups = sVerticalViews
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
        addModule(moduleName, extend, null)
    }

    fun addModule(moduleName: String?, extend: Bundle?, listener: ModuleLoadListener?) {
        if (moduleName != null && !moduleName.isEmpty()) {
            if (moduleManager.allModules.containsKey(moduleName)) return
            var module = moduleManager.getModuleByNames(moduleName)
            if (module == null) {
                module = CWModuleExFactory.newModuleInstance(moduleName)
            }
            if (moduleContext != null && module != null) {
                val result = module.onCreate(moduleContext, extend)
                listener?.laodResult(result)
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
        if (moduleName != null && !moduleName.isEmpty()) {
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
        if (moduleName != null && !moduleName.isEmpty()) {
            val module = moduleManager.getModuleByNames(moduleName)
            module?.setVisible(isVisible)
        }
    }
}