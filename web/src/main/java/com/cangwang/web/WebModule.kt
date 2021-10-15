package com.cangwang.web

import com.cangwang.base.util.ViewUtil.replaceFragment
import com.cangwang.base.util.ViewUtil.removeFragment
import com.cangwang.annotation.ModuleUnit
import com.cangwang.enums.LayoutLevel
import com.cangwang.core.cwmodule.ex.CWBasicExModule
import com.cangwang.base.api.WebApi
import com.cangwang.core.cwmodule.api.ModuleBackpress
import com.cangwang.core.cwmodule.CWModuleContext
import android.os.Bundle
import com.cangwang.core.cwmodule.api.BackPressStack
import androidx.fragment.app.Fragment

/**
 * 网页控制
 * Created by canwang on 2018/2/6.
 */
@ModuleUnit(templet = "top", layoutlevel = LayoutLevel.VERY_HIGHT)
class WebModule : CWBasicExModule(), WebApi, ModuleBackpress {
    private var wf: Fragment? = null
    private var isInit = false
    override fun onCreate(moduleContext: CWModuleContext, extend: Bundle?): Boolean {
        super.onCreate(moduleContext, extend)
        //        initView();
        registerMApi(WebApi::class.java, this)
        return true
    }

    private fun initView() {
        setContentView(R.layout.web_layout, parentTop)
        isInit = true
    }

    override fun loadWeb(url: String?, title: String?) {
        if (!isInit) {
            initView()
        }
        val bundle = Bundle()
        bundle.putString("url", url)
        bundle.putString("title", title)
        wf = replaceFragment(context, R.id.web_layout, context!!.supportFragmentManager, bundle, WebFragment::class.java, WebFragment.Companion.TAG)
    }

    override fun removeWeb() {
        wf = null
        removeFragment(context, context!!.supportFragmentManager, WebFragment.Companion.TAG, false)
        if (BackPressStack.stack.contains(this)) BackPressStack.stack.remove(this)
    }

    override fun onBackPress(): Boolean {
        if (wf != null) {
            (wf as WebFragment).webBack()
            return true
        }
        return true
    }

    override fun onDestroy() {
        unregisterMApi(WebApi::class.java)
        isInit = false
        super.onDestroy()
    }
}