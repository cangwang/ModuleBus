package com.cangwang.enums;

/**
 * 布局层级
 * Created by cangwang on 2017/8/31.
 */

public enum  LayoutLevel {
    VERY_HIGHT(100),
    HIGHT(200),
    NORMAL(300),
    LOW(400),
    VERY_LOW(500);

    public int getValue(){return value;}
    private int value;
    LayoutLevel(int value){this.value = value;}
}
