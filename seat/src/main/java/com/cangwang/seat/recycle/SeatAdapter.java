package com.cangwang.seat.recycle;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cangwang.base.api.AnchorApi;
import com.cangwang.base.ui.CircleImageView;
import com.cangwang.core.ModuleApiManager;
import com.cangwang.seat.R;
import com.cangwang.seat.bean.SeatInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cangwang on 2018/2/7.
 */

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatHolder>{

    private List<SeatInfo> list= new ArrayList<>();
    private Context context;

    public SeatAdapter(Context context){
        this.context = context;
    }

    public void addMsg(SeatInfo message){
        if (message!=null) {
            list.add(message);
            notifyDataSetChanged();
        }

    }

    public void addMsgList(List<SeatInfo> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public SeatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seat_item,parent,false);
        return new SeatHolder(view);
    }



    @Override
    public void onBindViewHolder(SeatHolder holder, int position) {
        SeatInfo message = list.get(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SeatHolder extends RecyclerView.ViewHolder{
        CircleImageView seatImg;

        public SeatHolder(final View itemView){
            super(itemView);
            seatImg = (CircleImageView)itemView.findViewById(R.id.seat_item);
            seatImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModuleApiManager.getInstance().getApi(AnchorApi.class).showAnchor((FragmentActivity) itemView.getContext(),null,null);
                }
            });
        }
    }
}
