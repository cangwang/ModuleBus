package com.cangwang.lamp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.cangwang.annotation.ModuleGroup;
import com.cangwang.annotation.ModuleUnit;
import com.cangwang.core.cwmodule.CWModuleContext;
import com.cangwang.core.cwmodule.ex.CWBasicExModule;
import com.cangwang.enums.LayoutLevel;
import com.cangwang.lamp.view.HeartLayout;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 氛围灯气泡
 * Created by cangwang on 2018/2/9.
 */
@ModuleGroup({
        @ModuleUnit(templet = "top",layoutlevel = LayoutLevel.LOW),
})
public class LampModule extends CWBasicExModule{
    private Timer mTimer = new Timer();
    private Random mRandom = new Random();
    private HeartLayout mHeartLayout;

    @Override
    public boolean init(CWModuleContext moduleContext, Bundle extend) {
        super.init(moduleContext, extend);
        initView();
        return true;
    }

    public void initView(){
        setContentView(R.layout.lamp_layout,parentBottom);
        mHeartLayout = findViewById(R.id.heart_layout);
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
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHeartLayout.addHeart(randomColor());
            }
        });
    }

    private int randomColor() {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
    }
}
