package com.cangwang.base.api

import androidx.fragment.app.FragmentActivity
import com.cangwang.core.MBaseApi

/**
 * Created by cangwang on 2018/2/7.
 */
interface AnchorApi : MBaseApi {
    fun showAnchor(context: FragmentActivity?, user: String?, url: String?)
}