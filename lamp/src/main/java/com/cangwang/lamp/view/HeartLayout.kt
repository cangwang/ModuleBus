/*
 * Copyright (C) 2015 tyrantgit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cangwang.lamp.view

import android.content.Context
import com.cangwang.lamp.R
import android.widget.RelativeLayout
import android.util.AttributeSet

class HeartLayout : RelativeLayout {
    private var mAnimator: AbstractPathAnimator? = null

    constructor(context: Context?) : super(context) {
        init(null, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        val a = context.obtainStyledAttributes(
                attrs, R.styleable.lamp_HeartLayout, defStyleAttr, 0)
        mAnimator = PathAnimator(AbstractPathAnimator.Config.Companion.fromTypeArray(a))
        a.recycle()
    }

    var animator: AbstractPathAnimator?
        get() = mAnimator
        set(animator) {
            clearAnimation()
            mAnimator = animator
        }

    override fun clearAnimation() {
        for (i in 0 until childCount) {
            getChildAt(i).clearAnimation()
        }
        removeAllViews()
    }

    fun addHeart(color: Int) {
        val heartView = HeartView(context)
        heartView.setColor(color)
        mAnimator!!.start(heartView, this)
    }

    fun addHeart(color: Int, heartResId: Int, heartBorderResId: Int) {
        val heartView = HeartView(context)
        heartView.setColorAndDrawables(color, heartResId, heartBorderResId)
        mAnimator!!.start(heartView, this)
    }
}