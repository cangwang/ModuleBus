package com.cangwang.chat.bean;

import com.cangwang.base.util.ColorUtil;

/**发言条目
 * Created by cangwang on 2018/2/5.
 */

public class ChatMessage {
    public String user;
    public String text;
    public int color;

    public ChatMessage(String user,String text){
        this.user = user;
        this.text = text;
        color = ColorUtil.getRandomColor();
    }
}
