package com.cangwang.core.util

import android.app.Application

/**
 * Created by cangwang on 2017/2/25.
 */
interface ModuleImpl {
    fun onLoad(app: Application?)
}