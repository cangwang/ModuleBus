package com.cangwang.base.api

import com.cangwang.core.MBaseApi

/**
 * Created by cangwang on 2018/2/5.
 */
interface ChatApi : MBaseApi {
    fun addChatMsg(user: String?, text: String?): Boolean
}