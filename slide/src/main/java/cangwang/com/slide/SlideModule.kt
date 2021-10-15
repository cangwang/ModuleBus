package cangwang.com.slide

import com.cangwang.base.util.ViewUtil.replaceFragment
import com.cangwang.base.util.ViewUtil.show
import com.cangwang.base.util.ViewUtil.hide
import com.cangwang.annotation.ModuleUnit
import com.cangwang.enums.LayoutLevel
import com.cangwang.core.cwmodule.ex.CWBasicExModule
import com.cangwang.base.api.SlideApi
import com.cangwang.core.cwmodule.api.ModuleBackpress
import com.cangwang.core.cwmodule.CWModuleContext
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 * 侧边弹框
 * Created by canwang on 2018/2/10.
 */
@ModuleUnit(templet = "top", layoutlevel = LayoutLevel.VERY_HIGHT)
class SlideModule : CWBasicExModule(), SlideApi, ModuleBackpress {
    private var slideFragment: Fragment? = null
    private var isInit = false
    override fun onCreate(moduleContext: CWModuleContext, extend: Bundle?): Boolean {
        super.onCreate(moduleContext, extend)
        registerMApi(SlideApi::class.java, this)
        return true
    }

    fun initView() {
        setContentView(R.layout.slide_layout)
        setOnClickListener(View.OnClickListener { hide() })
        isInit = true
    }

    override fun show() {
        if (!isInit) initView()
        showModule()
        if (slideFragment == null) {
            slideFragment = replaceFragment(context, R.id.slide_frg, context!!.supportFragmentManager, null, SlideFragment::class.java, SlideFragment.Companion.TAG)
        } else {
            show(context!!.supportFragmentManager, slideFragment)
        }
    }

    override fun hide() {
        hideModule()
        if (slideFragment != null) {
            hide(context!!.supportFragmentManager, slideFragment)
        }
    }

    override fun onDestroy() {
        unregisterMApi(SlideApi::class.java)
        slideFragment = null
        isInit = false
        super.onDestroy()
    }

    override fun onBackPress(): Boolean {
        if (slideFragment != null) {
            hide()
        }
        return true
    }
}