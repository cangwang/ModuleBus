package com.cangwang.chat

import androidx.recyclerview.widget.RecyclerView
import com.cangwang.chat.bean.ChatMessage
import com.cangwang.annotation.ModuleGroup
import com.cangwang.annotation.ModuleUnit
import com.cangwang.enums.LayoutLevel
import com.cangwang.core.cwmodule.ex.CWBasicExModule
import com.cangwang.base.api.ChatApi
import com.cangwang.chat.recycle.ChatAdapter
import com.cangwang.core.cwmodule.CWModuleContext
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import java.util.ArrayList

/**
 * 发言模块
 * Created by cangwang on 2018/2/3.
 */
@ModuleGroup(ModuleUnit(templet = "top", layoutlevel = LayoutLevel.LOW))
class ChatModule : CWBasicExModule(), ChatApi {
    private var chatRecyle: RecyclerView? = null
    private var adapter: ChatAdapter? = null
    private var i = 10
    private var isScolling = false
    override fun onCreate(moduleContext: CWModuleContext, extend: Bundle?): Boolean {
        super.onCreate(moduleContext, extend)
        initView()
        initData()
        registerMApi(ChatApi::class.java, this)
        return true
    }

    private fun initView() {
        setContentView(R.layout.chat_layout)
        chatRecyle = findViewById(R.id.chat_recyle)
        adapter = ChatAdapter(context)
        chatRecyle?.layoutManager = LinearLayoutManager(context)
        chatRecyle?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                isScolling = newState == RecyclerView.SCROLL_STATE_IDLE
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
        chatRecyle?.adapter = adapter
    }

    private fun initData() {
        val list: MutableList<ChatMessage> = ArrayList()
        for (i in 0..9) {
            val m = ChatMessage("cw", "abc $i")
            list.add(m)
        }
        adapter!!.addMsgList(list)
        chatRecyle!!.smoothScrollToPosition(10)
        handler?.post(runChat)
    }

    var runChat: Runnable = object : Runnable {
        override fun run() {
            if (i < 50) {
                adapter!!.addMsg(ChatMessage("cw", "efg" + i++))
                if (isScolling) chatRecyle!!.smoothScrollToPosition(adapter!!.itemCount)
                handler?.postDelayed(this, 3000)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        handler?.post(runChat)
    }

    override fun onPause() {
        super.onPause()
        handler?.removeCallbacks(runChat)
    }

    override fun addChatMsg(user: String?, text: String?): Boolean {
        adapter?.addMsg(ChatMessage(user, text))
        chatRecyle?.smoothScrollToPosition(adapter!!.itemCount)
        return true
    }

    override fun onDestroy() {
        handler?.removeCallbacks(runChat)
        unregisterMApi(ChatApi::class.java)
        super.onDestroy()
    }
}