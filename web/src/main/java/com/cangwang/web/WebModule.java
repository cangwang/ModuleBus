package com.cangwang.web;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;

import com.cangwang.annotation.ModuleUnit;
import com.cangwang.base.api.WebApi;
import com.cangwang.base.util.ViewUtil;
import com.cangwang.core.MBaseApi;
import com.cangwang.core.ModuleApiManager;
import com.cangwang.core.cwmodule.CWModuleContext;
import com.cangwang.core.cwmodule.ex.CWBasicExModule;
import com.cangwang.enums.LayoutLevel;

/**
 * 网页控制
 * Created by canwang on 2018/2/6.
 */
@ModuleUnit(templet = "top",layoutlevel = LayoutLevel.VERY_HIGHT)
public class WebModule extends CWBasicExModule implements WebApi{
    private View webLayout;
    private Fragment wf;

    @Override
    public boolean init(CWModuleContext moduleContext, Bundle extend) {
        super.init(moduleContext, extend);
        initView();
        ModuleApiManager.getInstance().putApi(WebApi.class,this);
        return true;
    }

    private void initView(){
        webLayout = LayoutInflater.from(context).inflate(R.layout.web_layout,parentTop,true);
    }

    @Override
    public void loadWeb(String url, String title) {
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        bundle.putString("title",title);
        wf = ViewUtil.replaceFragment(context,R.id.web_layout,context.getSupportFragmentManager(),bundle,WebFragment.class,WebFragment.TAG);
    }

    @Override
    public void removeWeb(){
        wf= null;
        ViewUtil.removeFragment(context,context.getSupportFragmentManager(),WebFragment.TAG,false);
    }

    @Override
    public boolean onBackPress() {
        if (wf!=null) {
            ((WebFragment) wf).webBack();
            return true;
        }
        return false;
    }

}
