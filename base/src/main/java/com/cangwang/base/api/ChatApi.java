package com.cangwang.base.api;

import com.cangwang.core.MBaseApi;

/**
 * Created by cangwang on 2018/2/5.
 */

public interface ChatApi extends MBaseApi{
    boolean addChatMsg(String user,String text);
}
