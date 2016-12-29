package com.cangwang.core.util;

import java.util.Map;

/**
 * Created by air on 2016/12/26.
 */

public class ModuleUtil {

    public static boolean empty(Map<?,?> c){
        return c == null || c.isEmpty();
    }

    public static boolean empty(String s){
        return s == null || s.isEmpty();
    }
}
