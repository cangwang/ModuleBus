package com.cangwang.live

import com.cangwang.annotation.ModuleUnit
import com.cangwang.enums.LayoutLevel
import com.cangwang.core.cwmodule.ex.CWBasicExModule
import com.cangwang.core.cwmodule.CWModuleContext
import android.os.Bundle

/**
 * 视频模块
 * Created by cangwang on 2018/2/6.
 */
@ModuleUnit(templet = "video", layoutlevel = LayoutLevel.VERY_LOW)
class LiveModule : CWBasicExModule() {
    override fun onCreate(moduleContext: CWModuleContext, extend: Bundle?): Boolean {
        super.onCreate(moduleContext, extend)
        initView()
        return true
    }

    override fun onOrientationChanges(isLandscape: Boolean) {
        super.onOrientationChanges(isLandscape)
    }

    private fun initView() {
        setContentView(R.layout.live_layout, parentBottom)
    }
}