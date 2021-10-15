package com.cangwang.gift.view

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet

/**
 * author：Administrator on 2016/12/26 17:32
 * description:文件说明
 * version:版本
 */
class MyViewPager : ViewPager {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        var height = 0
        //下面遍历所有child的高度
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
            val h = child.measuredHeight
            if (h > height) //采用最大的view的高度。
                height = h
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height * 2,
                MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}