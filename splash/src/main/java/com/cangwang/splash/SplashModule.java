package com.cangwang.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Bundle;

import com.cangwang.annotation.ModuleGroup;
import com.cangwang.annotation.ModuleUnit;
import com.cangwang.base.api.SplashApi;
import com.cangwang.core.cwmodule.CWModuleContext;
import com.cangwang.core.cwmodule.ex.CWBasicExModule;
import com.cangwang.enums.LayoutLevel;
import com.cangwang.splash.view.GiftFrameLayout;
import com.cangwang.splash.view.GiftSendModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 送礼流光
 * Created by cangwang on 2018/2/11.
 */
@ModuleGroup({
        @ModuleUnit(templet = "top",layoutlevel = LayoutLevel.NORMAL),
})
public class SplashModule extends CWBasicExModule implements SplashApi{

    private GiftFrameLayout giftFrameLayout1;
    private GiftFrameLayout giftFrameLayout2;

    List<GiftSendModel> giftSendModelList = new ArrayList<GiftSendModel>();

    @Override
    public boolean init(CWModuleContext moduleContext, Bundle extend) {
        super.init(moduleContext, extend);
        initView();
        registerMApi(SplashApi.class,this);
        return true;
    }

    public void initView(){
        setContentView(R.layout.splash_layout);
        giftFrameLayout1 = findViewById(R.id.splash_layout1);
        giftFrameLayout2 = findViewById(R.id.splash_layout2);
    }

    @Override
    public void onDestroy() {
        unregisterMApi(SplashApi.class);
        super.onDestroy();
    }

    @Override
    public void splash() {
        starGiftAnimation(createGiftSendModel());
    }

    private GiftSendModel createGiftSendModel(){
        return new GiftSendModel((int)(Math.random()*10));
    }

    private void starGiftAnimation(GiftSendModel model){
        if (!giftFrameLayout1.isShowing()) {
            sendGiftAnimation(giftFrameLayout1,model);
        }else if(!giftFrameLayout2.isShowing()){
            sendGiftAnimation(giftFrameLayout2,model);
        }else{
            giftSendModelList.add(model);
        }
    }


    private void sendGiftAnimation(final GiftFrameLayout view, GiftSendModel model){
        view.setModel(model);
        AnimatorSet animatorSet = view.startAnimation(model.getGiftCount());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                synchronized (giftSendModelList) {
                    if (giftSendModelList.size() > 0) {
                        view.startAnimation(giftSendModelList.get(giftSendModelList.size() - 1).getGiftCount());
                        giftSendModelList.remove(giftSendModelList.size() - 1);
                    }
                }
            }
        });
    }
}
