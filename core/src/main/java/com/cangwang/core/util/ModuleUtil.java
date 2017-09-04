package com.cangwang.core.util;

import java.util.Map;

/**
 * Created by air on 2016/12/26.
 */

public class ModuleUtil {

    public static final String FACADE_PACKAGE = "com.cangwang.core";
    public static final String MODULE_UNIT = "ModuleUnit";
    public static final String SEPARATOR = "$$";
    public static final String NAME_OF_MODULEUNIT = MODULE_UNIT + SEPARATOR;
    public static final String ADDRESS_OF_MODULEUNIT = FACADE_PACKAGE+"."+MODULE_UNIT + SEPARATOR;

    public static boolean empty(Map<?,?> c){
        return c == null || c.isEmpty();
    }

    public static boolean empty(String s){
        return s == null || s.isEmpty();
    }
}
