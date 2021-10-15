package com.cangwang.seat

import androidx.recyclerview.widget.RecyclerView
import com.cangwang.seat.bean.SeatInfo
import com.cangwang.annotation.ModuleGroup
import com.cangwang.annotation.ModuleUnit
import com.cangwang.enums.LayoutLevel
import com.cangwang.core.cwmodule.ex.CWBasicExModule
import com.cangwang.seat.recycle.SeatAdapter
import com.cangwang.core.cwmodule.CWModuleContext
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import java.util.ArrayList

/**座位席模块
 * Created by cangwang on 2018/2/7.
 */
@ModuleGroup(ModuleUnit(templet = "top", layoutlevel = LayoutLevel.LOW))
class SeatModule : CWBasicExModule() {
    private var seatRecyle: RecyclerView? = null
    private var adapter: SeatAdapter? = null
    override fun onCreate(moduleContext: CWModuleContext, extend: Bundle?): Boolean {
        super.onCreate(moduleContext, extend)
        initView()
        initData()
        return true
    }

    fun initView() {
        setContentView(R.layout.seat_layout)
        seatRecyle = findViewById<RecyclerView>(R.id.seat_recyle)
        seatRecyle!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = SeatAdapter(context!!)
        seatRecyle!!.adapter = adapter
    }

    fun initData() {
        val list: MutableList<SeatInfo> = ArrayList()
        for (i in 0..9) {
            val m = SeatInfo("cw", "abc $i")
            list.add(m)
        }
        adapter!!.addMsgList(list)
    }
}