package com.cangwang.page_view;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cangwang.core.IBaseClient;
import com.cangwang.core.ModuleBus;
import com.cangwang.core.ModuleEvent;
import com.cangwang.core.cwmodule.CWModuleContext;
import com.cangwang.core.cwmodule.ex.CWBasicExModule;

/**
 * Created by air on 2017/1/13.
 */
//@ModuleUnit()
public class PageViewModule extends CWBasicExModule {

    private Activity activity;
    private ViewGroup parentViewGroup;
    private View pageNameView;
    private TextView pageTitle;

    @Override
    public boolean init(CWModuleContext moduleContext, Bundle extend) {
        super.init(moduleContext, extend);
        activity = moduleContext.getActivity();
        parentViewGroup = moduleContext.getView(0);
        this.moduleContext = moduleContext;
        initView();
        ModuleBus.getInstance().register(this);
        return true;
    }

    private void initView(){
        pageNameView = LayoutInflater.from(activity).inflate(R.layout.page_view_layout,parentViewGroup,true);
        pageTitle = (TextView) pageNameView.findViewById(R.id.page_view_title);
    }

    @Override
    public void onDestroy() {
        ModuleBus.getInstance().unregister(this);
        super.onDestroy();
    }

    @ModuleEvent(coreClientClass = IBaseClient.class)
    public void changeNameTxt(String name){
        pageTitle.setText(name);
    }
}
