package com.cangwang.page_name

import android.app.Application
import android.util.Log

import com.cangwang.core.util.ModuleImpl

/**
 * Created by cangwang on 2017/2/25.
 */

class NameCreate : ModuleImpl {
    override fun onLoad(app: Application) {
        for (i in 0..4) {
            Log.v("NameCreate", "NameCreate onLoad")
        }
    }
}
