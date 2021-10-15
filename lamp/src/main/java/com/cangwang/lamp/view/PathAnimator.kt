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

import android.graphics.*
import android.view.animation.LinearInterpolator
import android.view.animation.Animation
import android.os.Looper
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.animation.Transformation
import java.util.concurrent.atomic.AtomicInteger

class PathAnimator(config: Config) : AbstractPathAnimator(config) {
    private val mCounter = AtomicInteger(0)
    private val mHandler: Handler = Handler(Looper.getMainLooper())
    override fun start(child: View, parent: ViewGroup) {
        parent.addView(child, ViewGroup.LayoutParams(mConfig.heartWidth, mConfig.heartHeight))
        val anim = FloatAnimation(createPath(mCounter, parent, 2), randomRotation(), parent, child)
        anim.duration = mConfig.animDuration.toLong()
        anim.interpolator = LinearInterpolator()
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                mHandler.post { parent.removeView(child) }
                mCounter.decrementAndGet()
            }

            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationStart(animation: Animation) {
                mCounter.incrementAndGet()
            }
        })
        anim.interpolator = LinearInterpolator()
        child.startAnimation(anim)
    }

    internal class FloatAnimation(path: Path?, rotation: Float, parent: View, child: View) : Animation() {
        private val mPm: PathMeasure = PathMeasure(path, false)
        private val mView: View = child
        private val mDistance: Float = mPm.length
        private val mRotation: Float = rotation
        override fun applyTransformation(factor: Float, transformation: Transformation) {
            val matrix = transformation.matrix
            mPm.getMatrix(mDistance * factor, matrix, PathMeasure.POSITION_MATRIX_FLAG)
            mView.rotation = mRotation * factor
            var scale = 1f
            if (3000.0f * factor < 200.0f) {
                scale = scale(factor.toDouble(), 0.0, 0.06666667014360428, 0.20000000298023224, 1.100000023841858)
            } else if (3000.0f * factor < 300.0f) {
                scale = scale(factor.toDouble(), 0.06666667014360428, 0.10000000149011612, 1.100000023841858, 1.0)
            }
            mView.scaleX = scale
            mView.scaleY = scale
            transformation.alpha = 1.0f - factor
        }

        init {
            parent.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        }
    }

    companion object {
        private fun scale(a: Double, b: Double, c: Double, d: Double, e: Double): Float {
            return ((a - b) / (c - b) * (e - d) + d).toFloat()
        }
    }

}