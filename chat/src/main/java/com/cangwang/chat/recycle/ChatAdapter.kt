package com.cangwang.chat.recycle

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.cangwang.chat.recycle.ChatAdapter.ChatHolder
import com.cangwang.chat.bean.ChatMessage
import android.view.LayoutInflater
import com.cangwang.chat.R
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.ArrayList

/**发言适配器
 * Created by cangwang on 2018/2/5.
 */
class ChatAdapter(private val context: Context?) : RecyclerView.Adapter<ChatHolder>() {
    private val list: MutableList<ChatMessage> = ArrayList()
    fun addMsg(message: ChatMessage?) {
        if (message != null) {
            list.add(message)
            notifyDataSetChanged()
        }
    }

    fun addMsgList(list: List<ChatMessage>?) {
        this.list.addAll(list!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return ChatHolder(view)
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        val message = list[position]
        holder.chatText.text = message.user + ": " + message.text
        holder.chatText.setTextColor(message.color)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var chatText: TextView

        init {
            chatText = itemView.findViewById<View>(R.id.chat_item) as TextView
        }
    }
}