package com.cangwang.page_view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.cangwang.core.IBaseClient
import com.cangwang.core.ModuleBus
import com.cangwang.core.ModuleEvent
import com.cangwang.core.cwmodule.CWModuleContext
import com.cangwang.core.cwmodule.ex.CWBasicExModule

/**
 * Created by air on 2017/1/13.
 */
//@ModuleUnit()
class PageViewModule : CWBasicExModule() {

    private var activity: Activity? = null
    private var parentViewGroup: ViewGroup? = null
    private var pageNameView: View? = null
    private var pageTitle: TextView? = null

    override fun init(moduleContext: CWModuleContext, extend: Bundle): Boolean {
        super.init(moduleContext, extend)
        activity = moduleContext.activity
        parentViewGroup = moduleContext.getView(0)
        this.moduleContext = moduleContext
        initView()
        ModuleBus.getInstance().register(this)
        return true
    }

    private fun initView() {
        pageNameView = LayoutInflater.from(activity).inflate(R.layout.page_view_layout, parentViewGroup, true)
        pageTitle = pageNameView!!.findViewById<View>(R.id.page_view_title) as TextView
    }

    override fun onDestroy() {
        ModuleBus.getInstance().unregister(this)
        super.onDestroy()
    }

    @ModuleEvent(coreClientClass = IBaseClient::class)
    fun changeNameTxt(name: String) {
        pageTitle!!.text = name
    }
}
