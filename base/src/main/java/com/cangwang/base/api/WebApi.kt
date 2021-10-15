package com.cangwang.base.api

import com.cangwang.core.MBaseApi

/**
 * Created by cangwang on 2018/2/6.
 */
interface WebApi : MBaseApi {
    fun loadWeb(url: String?, title: String?)
    fun removeWeb()
}