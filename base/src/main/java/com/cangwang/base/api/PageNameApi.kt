package com.cangwang.base.api

import com.cangwang.core.MBaseApi

/**
 * Created by cangwang on 2018/1/7.
 */
interface PageNameApi : MBaseApi {
    fun changeNameTxt(name: String?)
}