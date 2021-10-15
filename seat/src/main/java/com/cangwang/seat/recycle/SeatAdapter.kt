package com.cangwang.seat.recycle

import android.content.Context
import com.cangwang.core.ModuleApiManager.Companion.instance
import androidx.recyclerview.widget.RecyclerView
import com.cangwang.seat.recycle.SeatAdapter.SeatHolder
import com.cangwang.seat.bean.SeatInfo
import android.view.LayoutInflater
import com.cangwang.seat.R
import com.cangwang.base.ui.CircleImageView
import com.cangwang.base.api.AnchorApi
import androidx.fragment.app.FragmentActivity
import android.view.View
import android.view.ViewGroup
import java.util.ArrayList

/**
 * Created by cangwang on 2018/2/7.
 */
class SeatAdapter(private val context: Context) : RecyclerView.Adapter<SeatHolder>() {
    private val list: MutableList<SeatInfo> = ArrayList()
    fun addMsg(message: SeatInfo?) {
        if (message != null) {
            list.add(message)
            notifyDataSetChanged()
        }
    }

    fun addMsgList(list: List<SeatInfo>?) {
        this.list.addAll(list!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.seat_item, parent, false)
        return SeatHolder(view)
    }

    override fun onBindViewHolder(holder: SeatHolder, position: Int) {
        val message = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class SeatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var seatImg: CircleImageView

        init {
            seatImg = itemView.findViewById<View>(R.id.seat_item) as CircleImageView
            seatImg.setOnClickListener { instance.getApi(AnchorApi::class.java)!!.showAnchor(itemView.context as FragmentActivity, null, null) }
        }
    }
}