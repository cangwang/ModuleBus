package com.cangwang.modulebus.ExModule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import com.cangwang.modulebus.R
import com.cangwang.base.util.ViewUtil
import com.cangwang.template.TemplateFragment

import android.content.res.Configuration

/**
 * Created by cangwang on 2017/6/15.
 */
class ModuleExActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_example)
        //        getSupportFragmentManager().beginTransaction().replace(R.id.container,new TemplateFragment()).commit();
        ViewUtil.replaceFragment(this, R.id.container, supportFragmentManager, null, TemplateFragment::class.java, TemplateFragment.TAG)
    }
}