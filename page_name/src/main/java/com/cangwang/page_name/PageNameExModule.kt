package com.cangwang.page_name

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

import com.cangwang.annotation.ModuleGroup
import com.cangwang.annotation.ModuleUnit
import com.cangwang.core.IBaseClient
import com.cangwang.core.ModuleBus
import com.cangwang.core.ModuleEvent
import com.cangwang.core.cwmodule.CWModuleContext
import com.cangwang.core.cwmodule.ex.CWBasicExModule
import com.cangwang.core.util.ModuleImpl
import com.cangwang.enums.LayoutLevel

/**
 * Created by cangwang on 2017/6/15
 */
@ModuleGroup(ModuleUnit(templet = "top", layoutlevel = LayoutLevel.LOW), ModuleUnit(templet = "normal", layoutlevel = LayoutLevel.VERY_LOW))
class PageNameExModule : CWBasicExModule(), ModuleImpl {
    private var pageNameView: View? = null
    private var pageTitle: TextView? = null

    override fun init(moduleContext: CWModuleContext, extend: Bundle): Boolean {
        super.init(moduleContext, extend)
        this.moduleContext = moduleContext
        initView()
        ModuleBus.getInstance().register(this)
        return true
    }

    private fun initView() {
        //        pageNameView = LayoutInflater.from(context).inflate(R.layout.page_name_layout,parentTop,false);
        pageNameView = initLayout(R.layout.page_name_layout, parentTop)
        //一定需使用此方式初始化控件，不然无法隐藏或运行中移除控件
        pageTitle = genericFindViewById(R.id.page_title)
    }

    override fun onDestroy() {
        ModuleBus.getInstance().unregister(this)
        super.onDestroy()
    }

    @ModuleEvent(coreClientClass = IBaseClient::class)
    fun changeNameTxt(name: String) {
        pageTitle!!.text = name
    }

    override fun onLoad(app: Application) {
        for (i in 0..4) {
            Log.v("PageNameModule", "PageNameModule onLoad")
        }
    }
}
