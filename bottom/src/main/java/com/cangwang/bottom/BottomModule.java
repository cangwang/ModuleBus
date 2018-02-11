package com.cangwang.bottom;

import android.os.Bundle;
import android.view.View;

import com.cangwang.annotation.ModuleGroup;
import com.cangwang.annotation.ModuleUnit;
import com.cangwang.base.api.SlideApi;
import com.cangwang.base.api.SplashApi;
import com.cangwang.base.ui.CircleImageView;
import com.cangwang.core.ModuleApiManager;
import com.cangwang.core.cwmodule.CWModuleContext;
import com.cangwang.core.cwmodule.ex.CWBasicExModule;
import com.cangwang.enums.LayoutLevel;

/**
 * 底部栏
 * Created by cangwang on 2018/2/10.
 */
@ModuleGroup({
        @ModuleUnit(templet = "top",layoutlevel = LayoutLevel.LOW),
})
public class BottomModule extends CWBasicExModule{
    private CircleImageView moreBtn;
    private CircleImageView chatBtn;
    private CircleImageView giftBtn;
    private View bottomLayout;
    private InputModule ipM;

    @Override
    public boolean init(CWModuleContext moduleContext, Bundle extend) {
        super.init(moduleContext, extend);
        initView();
        return true;
    }

    public void initView(){
        setContentView(R.layout.bottom_layout);
        bottomLayout = findViewById(R.id.bottom_layout);
        bottomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        chatBtn = findViewById(R.id.bottom_chat);
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomLayout.setVisibility(View.GONE);
                if (ipM ==null){
                    ipM = new InputModule();
                    ipM.init(moduleContext,null);
                }else {
                    ipM.setVisible(true);
                }

            }
        });
        moreBtn = findViewById(R.id.bottom_more_btn);
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModuleApiManager.getInstance().getApi(SlideApi.class).show();
            }
        });
        giftBtn = findViewById(R.id.bottom_gift);
        giftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModuleApiManager.getInstance().getApi(SplashApi.class).splash();
            }
        });
    }

    @Override
    public boolean onBackPress() {
        if (ipM!=null && ipM.isVisible()){
            ipM.setVisible(false);
            if (bottomLayout.getVisibility() == View.GONE)
                bottomLayout.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        ipM =null;
        super.onDestroy();
    }
}
