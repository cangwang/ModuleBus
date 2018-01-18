package com.cangwang.core.cwmodule.ex

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cangwang.core.cwmodule.CWModuleContext

import java.util.ArrayList

/**
 * Created by cangwang on 2016/12/26.
 */

open class CWBasicExModule : CWAbsExModule() {
    lateinit var context: Activity
    lateinit var mContext: FragmentActivity
    lateinit var moduleContext: CWModuleContext
    lateinit var handler: Handler
    lateinit var parentTop: ViewGroup
    lateinit var parentBottom: ViewGroup
    lateinit var parentPlugin: ViewGroup
    lateinit var own: View
    var viewList: MutableList<View>? = ArrayList()

    @CallSuper
    override fun init(moduleContext: CWModuleContext, extend: Bundle): Boolean {
        context = moduleContext.activity!!
        parentTop = moduleContext.getView(CWModuleContext.TOP_VIEW_GROUP)
        parentBottom = moduleContext.getView(CWModuleContext.BOTTOM_VIEW_GROUP)
        parentPlugin = moduleContext.getView(CWModuleContext.PLUGIN_CENTER_VIEW)
        handler = Handler()
        return true
    }

    fun <T : View> genericFindViewById(id: Int): T {
        //return返回view时,加上泛型T
        val view = context!!.findViewById<View>(id) as T
        viewList!!.add(view)
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {

    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onOrientationChanges(isLandscape: Boolean) {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    @CallSuper
    override fun detachView() {
        val viewGroup: ViewGroup?
        if (viewList != null && viewList!!.size > 0) {
            viewGroup = viewList!![0].parent as ViewGroup
            viewGroup?.removeAllViewsInLayout()
        }
    }

    @CallSuper
    override fun onDestroy() {

    }

    override fun setVisible(visible: Boolean) {
        val viewGroup: ViewGroup
        if (viewList != null && viewList!!.size > 0) {
            viewGroup = viewList!![0].parent as ViewGroup
            viewGroup.visibility = if (visible) View.VISIBLE else View.GONE
        }
    }

    fun initLayout(@LayoutRes id: Int, parentTop: ViewGroup): View {
        own = LayoutInflater.from(context).inflate(id, parentTop, true)
        return own
    }
}
