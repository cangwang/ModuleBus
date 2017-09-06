package com.cangwang.page_name;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cangwang.annotation.ModuleUnit;
import com.cangwang.core.IBaseClient;
import com.cangwang.core.ModuleBus;
import com.cangwang.core.ModuleEvent;
import com.cangwang.core.cwmodule.ELModuleContext;
import com.cangwang.core.cwmodule.ex.ELBasicExModule;
import com.cangwang.core.util.ModuleImpl;

/**
 * Created by cangwang on 2017/6/15
 */
@ModuleUnit(templet = "top,normal")
public class PageNameExModule extends ELBasicExModule implements ModuleImpl{
    private View pageNameView;
    private TextView pageTitle;

    @Override
    public boolean init(ELModuleContext moduleContext, Bundle extend) {
        super.init(moduleContext, extend);
        this.moduleContext = moduleContext;
        initView();
        ModuleBus.getInstance().register(this);
        return true;
    }

    private void initView(){
//        pageNameView = LayoutInflater.from(context).inflate(R.layout.page_name_layout,parentTop,false);
        pageNameView = initLayout(R.layout.page_name_layout,parentTop);
        //一定需使用此方式初始化控件，不然无法隐藏或运行中移除控件
        pageTitle = genericFindViewById(R.id.page_title);
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

    @Override
    public void onLoad(Application app) {
        for (int i=0;i<5;i++){
            Log.v("PageNameModule","PageNameModule onLoad");
        }
    }
}
