package com.cangwang.splash.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.graphics.drawable.AnimationDrawable
import android.animation.PropertyValuesHolder
import android.view.View
import android.widget.ImageView
import android.widget.TextView

/**
 * ${Author}: 徐坤
 * ${CreateTime}:16/5/24
 * ${Description}:
 * ${EMail}:xukun1007@163.com
 */
object GiftAnimationUtil {
    /**
     * @param target
     * @param star         动画起始坐标
     * @param end          动画终止坐标
     * @param duration     持续时间
     * @return
     * 创建一个从左到右的飞入动画
     * 礼物飞入动画
     */
    fun createFlyFromLtoR(target: View?, star: Float, end: Float, duration: Int, interpolator: TimeInterpolator?): ObjectAnimator {
        //1.个人信息先飞出来
        val anim1 = ObjectAnimator.ofFloat(target, "translationX",
                star, end)
        anim1.interpolator = interpolator
        anim1.duration = duration.toLong()
        return anim1
    }

    /**
     * @param target
     * @return
     * 播放帧动画
     */
    fun startAnimationDrawable(target: ImageView?): AnimationDrawable? {
        val animationDrawable = target!!.drawable as AnimationDrawable
        if (animationDrawable != null) {
            target.visibility = View.VISIBLE
            animationDrawable.start()
        }
        return animationDrawable
    }

    /**
     * @param target
     * @param drawable
     * 设置帧动画
     */
    fun setAnimationDrawable(target: ImageView, drawable: AnimationDrawable?) {
        target.background = drawable
    }

    /**
     * @param target
     * @param num
     * @return
     * 送礼数字变化
     */
    fun scaleGiftNum(target: TextView?, num: Int): ObjectAnimator {
        val anim4 = PropertyValuesHolder.ofFloat("scaleX",
                1.7f, 0.8f, 1f)
        val anim5 = PropertyValuesHolder.ofFloat("scaleY",
                1.7f, 0.8f, 1f)
        val anim6 = PropertyValuesHolder.ofFloat("alpha",
                1.0f, 0f, 1f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(target, anim4, anim5, anim6).setDuration(480)
        animator.repeatCount = num
        return animator
    }

    /**
     * @param target
     * @param star
     * @param end
     * @param duration
     * @param startDelay
     * @return
     * 向上飞 淡出
     */
    fun createFadeAnimator(target: View?, star: Float, end: Float, duration: Int, startDelay: Int): ObjectAnimator {
        val translationY = PropertyValuesHolder.ofFloat("translationY", star, end)
        val alpha = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(target, translationY, alpha)
        animator.startDelay = startDelay.toLong()
        animator.duration = duration.toLong()
        return animator
    }

    /**
     * @param animators
     * @return
     * 按顺序播放动画
     */
    fun startAnimation(animator1: ObjectAnimator?, animator2: ObjectAnimator?, animator3: ObjectAnimator?, animator4: ObjectAnimator?, animator5: ObjectAnimator?): AnimatorSet {
        val animSet = AnimatorSet()
        //        animSet.playSequentially(animators);
        animSet.play(animator1).before(animator2)
        animSet.play(animator3).after(animator2)
        animSet.play(animator4).after(animator3)
        animSet.play(animator5).after(animator4)
        animSet.start()
        return animSet
    }
}