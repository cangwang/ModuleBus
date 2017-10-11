package com.cangwang.enums;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 布局层级
 * Created by cangwang on 2017/8/31.
 */

//public enum  LayoutLevel {
//    VERY_HIGHT(100),
//    HIGHT(200),
//    NORMAL(300),
//    LOW(400),
//    VERY_LOW(500);
//
//    public int getValue(){return value;}
//    private int value;
//    LayoutLevel(int value){this.value = value;}
//}

@IntDef({LayoutLevel.VERY_HIGHT,LayoutLevel.HIGHT,LayoutLevel.NORMAL,LayoutLevel.LOW,LayoutLevel.VERY_LOW})
@Retention(RetentionPolicy.SOURCE)
public @interface LayoutLevel{
    int VERY_HIGHT = 100;
    int HIGHT =200;
    int NORMAL =300;
    int LOW =400;
    int VERY_LOW = 500;
}