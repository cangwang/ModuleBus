package com.cangwang.base.api;

import com.cangwang.core.MBaseApi;

/**
 * Created by cangwang on 2018/2/6.
 */

public interface WebApi extends MBaseApi{
    void loadWeb(String url,String title);
    void removeWeb();
}
