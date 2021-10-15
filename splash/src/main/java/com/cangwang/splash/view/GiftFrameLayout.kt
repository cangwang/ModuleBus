package com.cangwang.splash.view

import android.animation.*
import com.cangwang.splash.R
import android.widget.FrameLayout
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.view.animation.OvershootInterpolator
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView

/**
 * ${Author}: 徐坤
 * ${CreateTime}:16/5/26
 * ${Description}:送礼的动画
 * ${EMail}:xukun1007@163.com
 */
class GiftFrameLayout @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) : FrameLayout(context!!, attrs) {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var anim_rl: RelativeLayout? = null
    var anim_gift: ImageView? = null
    var anim_light: ImageView? = null
    var anim_header: ImageView? = null
    var anim_nickname: TextView? = null
    var anim_sign: TextView? = null
    var anim_num: StrokeTextView? = null

    /**
     * 礼物数量的起始值
     */
    var starNum = 1
    var repeatCount = 0
    var isShowing = false
        private set

    private fun initView() {
        val view = mInflater.inflate(R.layout.splash_anim, this, false)
        anim_rl = view.findViewById<View>(R.id.animation_person_rl) as RelativeLayout
        anim_gift = view.findViewById<View>(R.id.animation_gift) as ImageView
        anim_light = view.findViewById<View>(R.id.animation_light) as ImageView
        anim_num = view.findViewById<View>(R.id.animation_num) as StrokeTextView
        anim_header = view.findViewById<View>(R.id.gift_userheader_iv) as ImageView
        anim_nickname = view.findViewById<View>(R.id.gift_usernickname_tv) as TextView
        anim_sign = view.findViewById<View>(R.id.gift_usersign_tv) as TextView
        this.addView(view)
        anim_rl!!.visibility = INVISIBLE
    }

    fun hideView() {
        anim_gift!!.visibility = INVISIBLE
        anim_light!!.visibility = INVISIBLE
        anim_num!!.visibility = INVISIBLE
    }

    fun setModel(model: GiftSendModel) {
        if (0 != model.giftCount) {
            repeatCount = model.giftCount
        }
        if (!TextUtils.isEmpty(model.nickname)) {
            anim_nickname?.text = model.nickname
        }
        if (!TextUtils.isEmpty(model.sig)) {
            anim_sign?.text = model.sig
        }
    }

    fun startAnimation(repeatCount: Int): AnimatorSet? {
        anim_rl!!.visibility = VISIBLE
        hideView()
        //布局飞入
        val flyFromLtoR = GiftAnimationUtil.createFlyFromLtoR(anim_rl, -width.toFloat(), 0f, 400, OvershootInterpolator())
        flyFromLtoR.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                this@GiftFrameLayout.visibility = VISIBLE
                this@GiftFrameLayout.alpha = 1f
                isShowing = true
                anim_num!!.text = "x " + 1
                Log.i("TAG", "flyFromLtoR A start")
            }
        })
        //礼物飞入
        val flyFromLtoR2 = GiftAnimationUtil.createFlyFromLtoR(anim_gift, -width.toFloat(), 0f, 400, DecelerateInterpolator())
        flyFromLtoR2.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                anim_gift!!.visibility = VISIBLE
            }

            override fun onAnimationEnd(animation: Animator) {
                GiftAnimationUtil.startAnimationDrawable(anim_light)
                anim_num!!.visibility = VISIBLE
            }
        })
        //数量增加
        val scaleGiftNum = GiftAnimationUtil.scaleGiftNum(anim_num, repeatCount)
        scaleGiftNum.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationRepeat(animation: Animator) {
                anim_num!!.text = "x " + ++starNum
            }
        })
        //向上渐变消失
        val fadeAnimator = GiftAnimationUtil.createFadeAnimator(this@GiftFrameLayout, 0f, -100f, 300, 400)
        fadeAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                this@GiftFrameLayout.visibility = INVISIBLE
            }
        })
        // 复原
        val fadeAnimator2 = GiftAnimationUtil.createFadeAnimator(this@GiftFrameLayout, 100f, 0f, 20, 0)
        val animatorSet = GiftAnimationUtil.startAnimation(flyFromLtoR, flyFromLtoR2, scaleGiftNum, fadeAnimator, fadeAnimator2)
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                starNum = 1
                isShowing = false
            }
        })
        return animatorSet
    }

    init {
        initView()
        hideView()
    }
}