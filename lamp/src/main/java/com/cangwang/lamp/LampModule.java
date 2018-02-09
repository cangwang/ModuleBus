package com.cangwang.lamp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.cangwang.core.cwmodule.CWModuleContext;
import com.cangwang.core.cwmodule.ex.CWBasicExModule;
import com.cangwang.lamp.view.HeartLayout;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 氛围灯气泡
 * Created by cangwang on 2018/2/9.
 */

public class LampModule extends CWBasicExModule{
    private View lampLayout;
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
        lampLayout = LayoutInflater.from(context).inflate(R.layout.lamp_layout,parentBottom,true);
        mHeartLayout = (HeartLayout) lampLayout.findViewById(R.id.heart_layout);
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mHeartLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mHeartLayout.addHeart(randomColor());
                    }
                });
            }
        }, 500, 200);
    }

    private int randomColor() {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
    }
}
