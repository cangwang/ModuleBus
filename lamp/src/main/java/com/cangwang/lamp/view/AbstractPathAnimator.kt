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

import com.cangwang.lamp.R
import android.content.res.TypedArray
import android.graphics.*
import android.view.View
import android.view.ViewGroup
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

abstract class AbstractPathAnimator(protected val mConfig: Config) {
    private val mRandom: Random
    fun randomRotation(): Float {
        return mRandom.nextFloat() * 28.6f - 14.3f
    }

    fun createPath(counter: AtomicInteger, view: View, factor: Int): Path {
        var factor = factor
        val r = mRandom
        var x = r.nextInt(mConfig.xRand)
        var x2 = r.nextInt(mConfig.xRand)
        val y = view.height - mConfig.initY
        var y2 = counter.toInt() * 15 + mConfig.animLength * factor + r.nextInt(mConfig.animLengthRand)
        factor = y2 / mConfig.bezierFactor
        x = mConfig.xPointFactor + x
        x2 = mConfig.xPointFactor + x2
        val y3 = y - y2
        y2 = y - y2 / 2
        val p = Path()
        p.moveTo(mConfig.initX.toFloat(), y.toFloat())
        p.cubicTo(mConfig.initX.toFloat(), (y - factor).toFloat(), x.toFloat(), (y2 + factor).toFloat(), x.toFloat(), y2.toFloat())
        p.moveTo(x.toFloat(), y2.toFloat())
        p.cubicTo(x.toFloat(), (y2 - factor).toFloat(), x2.toFloat(), (y3 + factor).toFloat(), x2.toFloat(), y3.toFloat())
        return p
    }

    abstract fun start(child: View, parent: ViewGroup)
    class Config {
        var initX = 0
        var initY = 0
        var xRand = 0
        var animLengthRand = 0
        var bezierFactor = 0
        var xPointFactor = 0
        var animLength = 0
        var heartWidth = 0
        var heartHeight = 0
        var animDuration = 0

        companion object {
            fun fromTypeArray(typedArray: TypedArray): Config {
                val config = Config()
                val res = typedArray.resources
                config.initX = typedArray.getDimension(R.styleable.lamp_HeartLayout_lamp_initX,
                        res.getDimensionPixelOffset(R.dimen.heart_anim_init_x).toFloat()).toInt()
                config.initY = typedArray.getDimension(R.styleable.lamp_HeartLayout_lamp_initY,
                        res.getDimensionPixelOffset(R.dimen.heart_anim_init_y).toFloat()).toInt()
                config.xRand = typedArray.getDimension(R.styleable.lamp_HeartLayout_lamp_xRand,
                        res.getDimensionPixelOffset(R.dimen.heart_anim_bezier_x_rand).toFloat()).toInt()
                config.animLength = typedArray.getDimension(R.styleable.lamp_HeartLayout_lamp_animLength,
                        res.getDimensionPixelOffset(R.dimen.heart_anim_length).toFloat()).toInt()
                config.animLengthRand = typedArray.getDimension(R.styleable.lamp_HeartLayout_lamp_animLengthRand,
                        res.getDimensionPixelOffset(R.dimen.heart_anim_length_rand).toFloat()).toInt()
                config.bezierFactor = typedArray.getInteger(R.styleable.lamp_HeartLayout_lamp_bezierFactor,
                        res.getInteger(R.integer.heart_anim_bezier_factor))
                config.xPointFactor = typedArray.getDimension(R.styleable.lamp_HeartLayout_lamp_xPointFactor,
                        res.getDimensionPixelOffset(R.dimen.heart_anim_x_point_factor).toFloat()).toInt()
                config.heartWidth = typedArray.getDimension(R.styleable.lamp_HeartLayout_lamp_heart_width,
                        res.getDimensionPixelOffset(R.dimen.heart_size_width).toFloat()).toInt()
                config.heartHeight = typedArray.getDimension(R.styleable.lamp_HeartLayout_lamp_heart_height,
                        res.getDimensionPixelOffset(R.dimen.heart_size_height).toFloat()).toInt()
                config.animDuration = typedArray.getInteger(R.styleable.lamp_HeartLayout_lamp_anim_duration,
                        res.getInteger(R.integer.anim_duration))
                return config
            }
        }
    }

    init {
        mRandom = Random()
    }
}