package com.cangwang.chat.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cangwang.chat.R;
import com.cangwang.chat.bean.ChatMessage;

import java.util.ArrayList;
import java.util.List;

/**发言适配器
 * Created by cangwang on 2018/2/5.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder>{

    private List<ChatMessage> list= new ArrayList<>();
    private Context context;

    public ChatAdapter(Context context){
        this.context = context;
    }

    public void addMsg(ChatMessage message){
        if (message!=null) {
            list.add(message);
            notifyDataSetChanged();
        }

    }

    public void addMsgList(List<ChatMessage> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,parent,false);
        return new ChatHolder(view);
    }



    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {
        ChatMessage message = list.get(position);
        holder.chatText.setText(message.text);
        holder.chatText.setTextColor(message.color);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ChatHolder extends RecyclerView.ViewHolder{
        TextView chatText;

        public ChatHolder(View itemView){
            super(itemView);
            chatText= (TextView)itemView.findViewById(R.id.chat_item);
        }
    }
}
