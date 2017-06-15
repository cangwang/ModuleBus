package com.cangwang.modulebus.ExModule;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cangeang on 17/6/15.
 */
public class PageExConfig {
    public static List<String> pageTitles = new ArrayList<String>();

    public static List<String> getPageTitles(Context context) {
        pageTitles.clear();
        pageTitles.add("a");
        pageTitles.add("b");
        pageTitles.add("b");
        pageTitles.add("b");
        return pageTitles;
    }

    private static final String FragmentA = "com.cangwang.a.FragmentA";

    private static final String FragmentB = "com.cangwang.b.FragmentB";

    public static String[] fragmentNames = {
            FragmentA,
            FragmentB,
            FragmentB,
            FragmentB
    };

    public static final String ActivityB = "com.cangwang.b.BActivity";


    public static final String MODULE_PAGE_NAME ="com.cangwang.page_name.PageNameExModule";
    public static final String MODULE_BODY_NAME ="com.cangwang.page_body.PageBodyExModule";
    public static final String MODULE_BODY_BT_NAME ="com.cangwang.page_body_bt.PageBodyBTModule";

    public static final String MODULE_VIEW_PAGE_NAME ="com.cangwang.page_view.PageViewModule";

    public static final String BODY_CREATE ="com.cangwang.page_body.BodyCreate";
    public static final String NAME_CREATE ="com.cangwang.page_name.NameCreate";

    public static List<String> moduleList = new ArrayList<>();


    public static final String[] moduleCreate = {
            BODY_CREATE,
            NAME_CREATE
    };

}
