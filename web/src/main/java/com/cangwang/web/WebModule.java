package com.cangwang.web;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cangwang.annotation.ModuleUnit;
import com.cangwang.base.api.WebApi;
import com.cangwang.base.util.ViewUtil;
import com.cangwang.core.cwmodule.CWModuleContext;
import com.cangwang.core.cwmodule.api.BackPressStack;
import com.cangwang.core.cwmodule.api.ModuleBackpress;
import com.cangwang.core.cwmodule.ex.CWBasicExModule;
import com.cangwang.enums.LayoutLevel;

/**
 * 网页控制
 * Created by canwang on 2018/2/6.
 */
@ModuleUnit(templet = "top",layoutlevel = LayoutLevel.VERY_HIGHT)
public class WebModule extends CWBasicExModule implements WebApi,ModuleBackpress{
    private Fragment wf;
    private boolean isInit;

    @Override
    public boolean onCreate(CWModuleContext moduleContext, Bundle extend) {
        super.onCreate(moduleContext, extend);
//        initView();
        registerMApi(WebApi.class,this);
        return true;
    }

    private void initView(){
        setContentView(R.layout.web_layout,parentTop);
        isInit = true;
    }

    @Override
    public void loadWeb(String url, String title) {
        if (!isInit){
            initView();
        }
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        bundle.putString("title",title);
        wf = ViewUtil.replaceFragment(context,R.id.web_layout,context.getSupportFragmentManager(),bundle,WebFragment.class,WebFragment.TAG);
    }

    @Override
    public void removeWeb(){
        wf= null;
        ViewUtil.removeFragment(context,context.getSupportFragmentManager(),WebFragment.TAG,false);
        if (BackPressStack.getInstance().getStack().contains(this))
            BackPressStack.getInstance().getStack().remove(this);
    }

    @Override
    public boolean onBackPress() {
        if (wf!=null) {
            ((WebFragment) wf).webBack();
            return true;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        unregisterMApi(WebApi.class);
        isInit = false;
        super.onDestroy();
    }
}
