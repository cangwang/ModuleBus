package com.cangwang.chat.bean

import com.cangwang.base.util.ColorUtil.randomColor
import com.cangwang.annotation.ModuleBean
import com.cangwang.base.util.ColorUtil
import androidx.recyclerview.widget.RecyclerView
import com.cangwang.chat.recycle.ChatAdapter.ChatHolder
import com.cangwang.chat.bean.ChatMessage
import android.view.LayoutInflater
import com.cangwang.chat.R
import com.cangwang.annotation.ModuleGroup
import com.cangwang.annotation.ModuleUnit
import com.cangwang.enums.LayoutLevel
import com.cangwang.core.cwmodule.ex.CWBasicExModule
import com.cangwang.base.api.ChatApi
import com.cangwang.chat.recycle.ChatAdapter
import com.cangwang.core.cwmodule.CWModuleContext
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager

/**发言条目
 * Created by cangwang on 2018/2/5.
 */
@ModuleBean
class ChatMessage(var user: String?, var text: String?) {
    var color: Int

    init {
        color = randomColor
    }
}