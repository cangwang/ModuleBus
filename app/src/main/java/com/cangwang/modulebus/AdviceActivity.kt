package com.cangwang.modulebus

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager

import com.cangwang.modulebus.ExModule.ModuleMainExActivity

/**
 * Created by cangwang on 2017/2/26.
 */

class AdviceActivity : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        startActivity(Intent(this, ModuleMainExActivity::class.java))
        finish()
    }
}
