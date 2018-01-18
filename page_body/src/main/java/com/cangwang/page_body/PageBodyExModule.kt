package com.cangwang.page_body

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView

import com.cangwang.annotation.ModuleUnit
import com.cangwang.core.IBaseClient
import com.cangwang.core.ModuleBus
import com.cangwang.core.cwmodule.CWModuleContext
import com.cangwang.core.cwmodule.ex.CWBasicExModule
import com.cangwang.core.util.ModuleImpl
import com.cangwang.enums.LayoutLevel

/**
 * Created by cangwang on 2016/12/28.
 */

@ModuleUnit(templet = "top", layoutlevel = LayoutLevel.LOW, extralevel = 11)
class PageBodyExModule : CWBasicExModule(), ModuleImpl {
    private var pageBodyView_fi: View? = null
    private var pageBodyView_se: View? = null
    private var pageBodyTop: TextView? = null
    private val pageBodyBottom: TextView? = null
    private var changeNameBtn: Button? = null
    private var addTitle: Button? = null
    private var removeTitle: Button? = null

    override fun init(moduleContext: CWModuleContext, extend: Bundle): Boolean {
        super.init(moduleContext, extend)
        this.moduleContext = moduleContext
        initView()
        return true
    }

    private fun initView() {
        //直接添加布局到父布局
        pageBodyView_fi = LayoutInflater.from(context).inflate(R.layout.page_body_fi, parentTop, true)
        pageBodyTop = pageBodyView_fi!!.findViewById<View>(R.id.page_body_top) as TextView
        //动态添加布局
        pageBodyView_se = LayoutInflater.from(context).inflate(R.layout.page_body_se, null)
        val rl = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        if (parentBottom != null)
            parentBottom.addView(pageBodyView_se, rl)

        removeTitle = genericFindViewById(R.id.remove_title)
        addTitle = genericFindViewById(R.id.add_title)

        removeTitle!!.setOnClickListener { ModuleBus.getInstance().post(IBaseClient::class.java, "removeModule", "com.cangwang.page_name.PageNameExModule") }

        addTitle!!.setOnClickListener {
            val bundle = Bundle()
            ModuleBus.getInstance().post(IBaseClient::class.java, "addModule", "com.cangwang.page_name.PageNameExModule", bundle)
        }

        changeNameBtn = pageBodyView_se!!.findViewById<View>(R.id.change_page_Name) as Button
        changeNameBtn!!.setOnClickListener {
            //                ModuleBus.getInstance().post(IBaseClient.class,"changeNameTxt","Cang_Wang");
            context!!.startActivity(Intent("com.cangwang.moduleExFg"))
        }
    }

    override fun onLoad(app: Application) {
        for (i in 0..4) {
            Log.v("PageBodyModule", "PageBodyModule onLoad")
        }
    }
}

