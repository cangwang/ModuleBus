package com.cangwang.modulebus.ExModule

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.cangwang.modulebus.R

/**
 * Created by cangwang on 2017/6/15.
 */

class ModuleExActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        supportFragmentManager.beginTransaction().replace(R.id.container, ModuleExFragment()).commit()
    }
}
