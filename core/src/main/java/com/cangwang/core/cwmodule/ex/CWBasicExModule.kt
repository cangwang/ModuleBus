package com.cangwang.core.cwmodule.ex

import android.app.Activity
import com.cangwang.core.cwmodule.CWModuleContext
import android.os.Bundle
import com.cangwang.core.MBaseApi
import androidx.fragment.app.FragmentActivity
import android.view.LayoutInflater
import com.cangwang.core.cwmodule.api.ModuleBackpress
import androidx.annotation.CallSuper
import com.cangwang.core.cwmodule.api.BackPressStack
import androidx.annotation.LayoutRes
import com.cangwang.core.ModuleApiManager
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import java.util.*

/**
 * Created by cangwang on 2016/12/26.
 */
open class CWBasicExModule : CWAbsExModule() {
    @kotlin.jvm.JvmField
    var context: FragmentActivity? = null
    var moduleContext: CWModuleContext? = null
    var handler: Handler? = null
    @kotlin.jvm.JvmField
    protected var parentTop: ViewGroup? = null
    @kotlin.jvm.JvmField
    protected var parentBottom: ViewGroup? = null
    @kotlin.jvm.JvmField
    protected var parentPlugin: ViewGroup? = null
    private var rootView: View? = null
    private var isShow = false
    private var viewList: MutableList<View>? = null
    private var inflater: LayoutInflater? = null
    private var stack: Stack<ModuleBackpress>? = null
    var templateName: String? = null
    @CallSuper
    override fun onCreate(moduleContext: CWModuleContext, extend: Bundle?): Boolean {
        this.moduleContext = moduleContext
        context = moduleContext.activity
        inflater = moduleContext.inflater
        parentTop = moduleContext.getView(CWModuleContext.Companion.TOP_VIEW_GROUP)
        parentBottom = moduleContext.getView(CWModuleContext.Companion.BOTTOM_VIEW_GROUP)
        parentPlugin = moduleContext.getView(CWModuleContext.Companion.PLUGIN_CENTER_VIEW)
        handler = Handler()
        templateName = moduleContext.templateName
        viewList = ArrayList()
        stack = BackPressStack.stack
        return true
    }

    fun setContentView(@LayoutRes layoutResID: Int) {
        setContentView(layoutResID, parentPlugin)
    }

    fun setContentView(@LayoutRes layoutResID: Int, viewGroup: ViewGroup?) {
        setContentView(layoutResID, viewGroup, false)
    }

    fun setContentView(@LayoutRes layoutResID: Int, viewGroup: ViewGroup?, attachToRoot: Boolean) {
        rootView = inflater!!.inflate(layoutResID, viewGroup, attachToRoot)
        if (rootView != null && viewGroup != null) viewGroup.addView(rootView)
        isShow = true
        if (this is ModuleBackpress) {
            stack!!.push(this as ModuleBackpress)
            Log.e("BasicExModule", "push " + BackPressStack.stack.toString())
        }
    }

    fun <T : View?> findViewById(id: Int): T? {
        //return返回view时,加上泛型T
        if (context == null) return null
        val view: T? = context!!.findViewById<View>(id) as T
        if (viewList != null && view != null) viewList!!.add(view)
        return view
    }

    override fun onSaveInstanceState(outState: Bundle?) {}
    override fun onStart() {}
    override fun onResume() {}
    override fun onOrientationChanges(isLandscape: Boolean) {}
    override fun onPause() {}
    override fun onStop() {}
    val resources: Resources
        get() = context!!.resources
    val activity: Activity?
        get() = context

    @CallSuper
    override fun detachView() {
        val viewGroup: ViewGroup
        if (viewList != null && viewList!!.size > 0) {
            viewGroup = viewList!![0].parent as ViewGroup
            if (viewGroup != null) viewGroup.removeAllViewsInLayout()
        }
    }

    @CallSuper
    override fun onDestroy() {
        context = null
        moduleContext = null
        handler = null
        parentTop = null
        parentBottom = null
        parentPlugin = null
        rootView = null
        viewList = null
        isShow = false
        if (this is ModuleBackpress && stack!!.contains(this)) {
            stack!!.remove(this)
        }
    }

    override fun setVisible(visible: Boolean) {
        val viewGroup: ViewGroup
        if (viewList != null && viewList!!.size > 0) {
            viewGroup = viewList!![0].parent as ViewGroup
            viewGroup.visibility = if (visible) View.VISIBLE else View.GONE
        }
        setRootVisbile(if (visible) View.VISIBLE else View.GONE)
    }

    private fun setRootVisbile(visbile: Int) {
        isShow = visbile == View.VISIBLE
        if (this is ModuleBackpress) {
            if (isShow && !stack!!.contains(this)) {
                stack!!.push(this as ModuleBackpress)
                Log.i("BasicExModule", "push " + BackPressStack.stack.toString())
            } else if (!isShow && stack!!.contains(this)) {
                Log.e("BasicExModule", "pop " + BackPressStack.stack.toString())
                stack!!.pop()
            }
        }
    }

    fun isVisible(): Boolean {
        return isShow
    }

    open fun showModule() {
        if (rootView != null) rootView!!.visibility = View.VISIBLE
        setRootVisbile(View.VISIBLE)
    }

    open fun hideModule() {
        if (rootView != null) rootView!!.visibility = View.GONE
        if (this is ModuleBackpress && BackPressStack.stack.contains(this)) {
            BackPressStack.stack.pop()
        }
        setRootVisbile(View.GONE)
    }

    override fun registerMApi(key: Class<out MBaseApi?>?, value: MBaseApi?) {
        ModuleApiManager.instance.putApi(key, value)
    }

    override fun unregisterMApi(key: Class<out MBaseApi?>?) {
        ModuleApiManager.instance.removeApi(key)
    }

    fun setOnClickListener(listener: View.OnClickListener?) {
        rootView!!.setOnClickListener(listener)
    }// The name of the process that this object is associated with.

    /**
     * 获取Android设备中所有正在运行的App
     */
    val isAppOnForeground: Boolean
        get() {
            val activityManager = context!!.applicationContext
                    .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val packageName = context!!.applicationContext.packageName
            /**
             * 获取Android设备中所有正在运行的App
             */
            /**
             * 获取Android设备中所有正在运行的App
             */
            val appProcesses = activityManager
                    .runningAppProcesses ?: return false
            for (appProcess in appProcesses) {
                // The name of the process that this object is associated with.
                if (appProcess.processName == packageName && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true
                }
            }
            return false
        }
}