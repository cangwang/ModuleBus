package com.cangwang.gift.adapter

import android.content.Context
import com.cangwang.gift.bean.Gift
import android.view.LayoutInflater
import com.cangwang.gift.R
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.ArrayList

/**
 * author：Administrator on 2016/12/26 15:03
 * description:文件说明
 * version:版本
 */
///定影GridView的Adapter
class GiftGridViewAdapter(private val context: Context, private val page: Int, private val count: Int) : BaseAdapter() {
    private var gifts: ArrayList<Gift>? = null
    fun setGifts(gifts: ArrayList<Gift>?) {
        this.gifts = gifts
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        // TODO Auto-generated method stub
        return 8
    }

    override fun getItem(position: Int): Gift {
        // TODO Auto-generated method stub
        return gifts!![page * count + position]
    }

    override fun getItemId(position: Int): Long {
        // TODO Auto-generated method stub
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        // TODO Auto-generated method stub
        var convertView = convertView
        var viewHolder: ViewHolder? = null
        val catogary = gifts!![page * count + position]
        if (convertView == null) {
            viewHolder = ViewHolder()
            convertView = LayoutInflater.from(context).inflate(R.layout.gift_item, null)
            viewHolder.grid_fragment_home_item_img = convertView.findViewById<View>(R.id.grid_fragment_home_item_img) as ImageView
            viewHolder.grid_fragment_home_item_txt = convertView.findViewById<View>(R.id.grid_fragment_home_item_txt) as TextView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        //        viewHolder.grid_fragment_home_item_img.setImageResource(catogary.getImage_source());
        viewHolder.grid_fragment_home_item_txt!!.text = catogary.name
        viewHolder.grid_fragment_home_item_img!!.setImageResource(catogary.giftType)
        convertView?.setOnClickListener {
            clickListener?.click(catogary)
        }
        return convertView
    }

    inner class ViewHolder {
        var grid_fragment_home_item_img: ImageView? = null
        var grid_fragment_home_item_txt: TextView? = null
    }

    var clickListener: OnGridViewClickListener? = null
    fun setOnGridViewClickListener(clickListener: OnGridViewClickListener) {
        this.clickListener = clickListener
    }

    interface OnGridViewClickListener {
        fun click(gift: Gift)
    }
}