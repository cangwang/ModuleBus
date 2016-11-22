package com.cangwang.modulebus;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by air on 16/7/6.
 */
public class PageConfig {
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

}
