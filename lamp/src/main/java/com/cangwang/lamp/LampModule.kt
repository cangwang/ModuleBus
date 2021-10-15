package com.cangwang.lamp

import android.graphics.*
import com.cangwang.annotation.ModuleGroup
import com.cangwang.annotation.ModuleUnit
import com.cangwang.enums.LayoutLevel
import com.cangwang.core.cwmodule.ex.CWBasicExModule
import com.cangwang.lamp.view.HeartLayout
import com.cangwang.core.cwmodule.CWModuleContext
import android.os.Bundle
import android.view.View
import java.util.*

/**
 * 氛围灯气泡
 * Created by cangwang on 2018/2/9.
 */
@ModuleGroup(ModuleUnit(templet = "top", layoutlevel = LayoutLevel.LOW))
class LampModule : CWBasicExModule() {
    private val mTimer = Timer()
    private val mRandom = Random()
    private var mHeartLayout: HeartLayout? = null
    override fun onCreate(moduleContext: CWModuleContext, extend: Bundle?): Boolean {
        super.onCreate(moduleContext, extend)
        initView()
        return true
    }

    fun initView() {
        setContentView(R.layout.lamp_layout, parentBottom)
        mHeartLayout = findViewById<HeartLayout>(R.id.heart_layout)
        //        mTimer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                mHeartLayout.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mHeartLayout.addHeart(randomColor());
//                    }
//                });
//            }
//        }, 500, 200);
        setOnClickListener(View.OnClickListener { mHeartLayout!!.addHeart(randomColor()) })
    }

    private fun randomColor(): Int {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255))
    }
}