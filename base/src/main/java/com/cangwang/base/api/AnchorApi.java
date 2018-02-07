package com.cangwang.base.api;


import android.support.v4.app.FragmentActivity;

import com.cangwang.core.MBaseApi;

/**
 * Created by cangwang on 2018/2/7.
 */

public interface AnchorApi extends MBaseApi{
    void showAnchor(FragmentActivity context, String user, String url);
}
