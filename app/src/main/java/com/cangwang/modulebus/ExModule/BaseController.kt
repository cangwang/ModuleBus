package com.cangwang.modulebus.ExModule

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup

/**
 * Created by cangwang on 2017/6/15.
 */
class BaseController : IView {
    private var context: Context? = null
    fun onAttach(activity: Activity?) {
        context = activity
    }

    fun create(saveInstanceState: Bundle?, activity: Activity?, container: ViewGroup?) {}
    override val view: View?
        get() = null

    override fun hide() {}
    override fun show() {}
}