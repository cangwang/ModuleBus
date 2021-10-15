package com.cangwang.splash

import android.animation.*
import com.cangwang.splash.view.GiftSendModel
import com.cangwang.annotation.ModuleGroup
import com.cangwang.annotation.ModuleUnit
import com.cangwang.enums.LayoutLevel
import com.cangwang.core.cwmodule.ex.CWBasicExModule
import com.cangwang.base.api.SplashApi
import com.cangwang.splash.view.GiftFrameLayout
import com.cangwang.core.cwmodule.CWModuleContext
import android.os.Bundle
import java.util.ArrayList

/**
 * 送礼流光
 * Created by cangwang on 2018/2/11.
 */
@ModuleGroup(ModuleUnit(templet = "top", layoutlevel = LayoutLevel.NORMAL))
class SplashModule : CWBasicExModule(), SplashApi {
    private var giftFrameLayout1: GiftFrameLayout? = null
    private var giftFrameLayout2: GiftFrameLayout? = null
    var giftSendModelList: MutableList<GiftSendModel> = ArrayList()
    override fun onCreate(moduleContext: CWModuleContext, extend: Bundle?): Boolean {
        super.onCreate(moduleContext, extend)
        initView()
        registerMApi(SplashApi::class.java, this)
        return true
    }

    fun initView() {
        setContentView(R.layout.splash_layout)
        giftFrameLayout1 = findViewById<GiftFrameLayout>(R.id.splash_layout1)
        giftFrameLayout2 = findViewById<GiftFrameLayout>(R.id.splash_layout2)
    }

    override fun onDestroy() {
        unregisterMApi(SplashApi::class.java)
        super.onDestroy()
    }

    override fun splash() {
        starGiftAnimation(createGiftSendModel())
    }

    private fun createGiftSendModel(): GiftSendModel {
        return GiftSendModel((Math.random() * 10).toInt())
    }

    private fun starGiftAnimation(model: GiftSendModel) {
        if (!giftFrameLayout1!!.isShowing) {
            sendGiftAnimation(giftFrameLayout1, model)
        } else if (!giftFrameLayout2!!.isShowing) {
            sendGiftAnimation(giftFrameLayout2, model)
        } else {
            giftSendModelList.add(model)
        }
    }

    private fun sendGiftAnimation(view: GiftFrameLayout?, model: GiftSendModel) {
        view!!.setModel(model)
        val animatorSet = view.startAnimation(model.giftCount)
        animatorSet!!.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                synchronized(giftSendModelList) {
                    if (giftSendModelList.size > 0) {
                        view.startAnimation(giftSendModelList[giftSendModelList.size - 1].giftCount)
                        giftSendModelList.removeAt(giftSendModelList.size - 1)
                    }
                }
            }
        })
    }
}