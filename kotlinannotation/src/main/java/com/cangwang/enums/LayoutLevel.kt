package com.cangwang.enums

/**
 * 布局层级
 * Created by cangwang on 2017/8/31.
 */
enum class LayoutLevel(val value: Int = LayoutLevel.VERY_LOW.value) {
    VERY_HIGHT(100), HIGHT(200), NORMAL(300), LOW(400), VERY_LOW(500);

}