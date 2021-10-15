package com.cangwang.modulebus.ExModule

import android.os.Bundle
import android.view.WindowManager
import com.cangwang.modulebus.R
import com.cangwang.base.util.ViewUtil
import com.cangwang.template.TemplateFragment
import com.cangwang.core.cwmodule.ex.ModuleManageExActivity
import android.content.res.Configuration

/**
 * Created by cangwang on 2017/6/15.
 */
class ModuleMainExActivity : ModuleManageExActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) { //横屏
            val lp = window.attributes
            lp.flags = lp.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
            window.attributes = lp
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        } else if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) { //竖屏
            val lp = window.attributes
            lp.flags = lp.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
            window.attributes = lp
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        super.onCreate(savedInstanceState)
        setBackGroundResouce(R.color.black)
        ViewUtil.replaceFragment(this, R.id.layout_plugincenter, supportFragmentManager, null, TemplateFragment::class.java, TemplateFragment.TAG)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun moduleConfig(): String {
        return "video"
    }
}