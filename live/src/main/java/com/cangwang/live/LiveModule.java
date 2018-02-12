package com.cangwang.live;

import android.os.Bundle;
import android.view.View;

import com.cangwang.annotation.ModuleUnit;
import com.cangwang.core.cwmodule.CWModuleContext;
import com.cangwang.core.cwmodule.ex.CWBasicExModule;
import com.cangwang.enums.LayoutLevel;

/**
 * 视频模块
 * Created by cangwang on 2018/2/6.
 */
@ModuleUnit(templet = "video",layoutlevel = LayoutLevel.VERY_LOW)
public class LiveModule extends CWBasicExModule{

    @Override
    public boolean init(CWModuleContext moduleContext, Bundle extend) {
        super.init(moduleContext, extend);
        initView();
        return true;
    }

    @Override
    public void onOrientationChanges(boolean isLandscape) {
        super.onOrientationChanges(isLandscape);

    }

    private void initView(){
        setContentView(R.layout.live_layout,parentBottom);
    }
}
