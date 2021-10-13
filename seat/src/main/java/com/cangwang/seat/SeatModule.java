package com.cangwang.seat;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cangwang.annotation.ModuleGroup;
import com.cangwang.annotation.ModuleUnit;
import com.cangwang.core.cwmodule.CWModuleContext;
import com.cangwang.core.cwmodule.ex.CWBasicExModule;
import com.cangwang.enums.LayoutLevel;
import com.cangwang.seat.bean.SeatInfo;
import com.cangwang.seat.recycle.SeatAdapter;

import java.util.ArrayList;
import java.util.List;

/**座位席模块
 * Created by cangwang on 2018/2/7.
 */
@ModuleGroup({
        @ModuleUnit(templet = "top",layoutlevel = LayoutLevel.LOW),
})
public class SeatModule extends CWBasicExModule{
    private RecyclerView seatRecyle;
    private SeatAdapter adapter;

    @Override
    public boolean onCreate(CWModuleContext moduleContext, Bundle extend) {
        super.onCreate(moduleContext, extend);
        initView();
        initData();
        return true;
    }

    public void initView(){
        setContentView(R.layout.seat_layout);
        seatRecyle = findViewById(R.id.seat_recyle);
        seatRecyle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        adapter = new SeatAdapter(context);
        seatRecyle.setAdapter(adapter);
    }

    public void initData(){
        List<SeatInfo> list =new ArrayList<>();
        for (int i=0;i<10;i++){
            SeatInfo m = new SeatInfo("cw","abc "+i);
            list.add(m);
        }
        adapter.addMsgList(list);
    }

}
