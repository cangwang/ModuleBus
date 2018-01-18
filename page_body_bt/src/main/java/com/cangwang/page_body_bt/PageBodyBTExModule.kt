package com.cangwang.page_body_bt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.cangwang.annotation.ModuleUnit
import com.cangwang.core.IBaseClient
import com.cangwang.core.ModuleBus
import com.cangwang.core.cwmodule.CWModuleContext
import com.cangwang.core.cwmodule.ex.CWBasicExModule

/**
 * Created by cangwang on 2017/6/15.
 */
@ModuleUnit
class PageBodyBTExModule : CWBasicExModule() {
    private var pageBodyView_bt: View? = null
    private var pageBodyView_bts: View? = null
    private var pageBodyTop: TextView? = null
    private val pageBodyBottom: TextView? = null
    private var changeNameBtn: Button? = null
    private var showTitle: Button? = null
    private var goneTitle: Button? = null

    override fun init(moduleContext: CWModuleContext, extend: Bundle): Boolean {
        super.init(moduleContext, extend)
        this.moduleContext = moduleContext
        initView()
        return true
    }

    private fun initView() {
        pageBodyView_bt = LayoutInflater.from(context).inflate(R.layout.page_body_bt, parentTop, true)
        pageBodyTop = genericFindViewById(R.id.page_body_top)

        showTitle = genericFindViewById(R.id.show_title)
        goneTitle = genericFindViewById(R.id.gone_title)

        showTitle!!.setOnClickListener { ModuleBus.getInstance().post(IBaseClient::class.java, "moduleVisible", "com.cangwang.page_name.PageNameExModule", true) }

        goneTitle!!.setOnClickListener { ModuleBus.getInstance().post(IBaseClient::class.java, "moduleVisible", "com.cangwang.page_name.PageNameExModule", false) }

        pageBodyView_bts = LayoutInflater.from(context).inflate(R.layout.page_body_bts, parentBottom, true)

        changeNameBtn = genericFindViewById(R.id.change_name)
        changeNameBtn!!.setOnClickListener { ModuleBus.getInstance().post(IBaseClient::class.java, "changeNameTxt", "Cang_Wang") }
    }
}
