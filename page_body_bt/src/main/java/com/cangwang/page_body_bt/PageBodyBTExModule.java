package com.cangwang.page_body_bt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cangwang.annotation.ModuleUnit;
import com.cangwang.base.api.PageNameApi;
import com.cangwang.core.IBaseClient;
import com.cangwang.core.ModuleApiManager;
import com.cangwang.core.ModuleBus;
import com.cangwang.core.cwmodule.CWModuleContext;
import com.cangwang.core.cwmodule.ex.CWBasicExModule;

/**
 * Created by cangwang on 2017/6/15.
 */
@ModuleUnit()
public class PageBodyBTExModule extends CWBasicExModule {
    private View pageBodyView_bt;
    private View pageBodyView_bts;
    private TextView pageBodyTop;
    private TextView pageBodyBottom;
    private Button changeNameBtn;
    private Button showTitle;
    private Button goneTitle;

    @Override
    public boolean init(CWModuleContext moduleContext, Bundle extend) {
        super.init(moduleContext, extend);
        this.moduleContext = moduleContext;
        initView();
        return true;
    }

    private void initView(){
        pageBodyView_bt = LayoutInflater.from(context).inflate(R.layout.page_body_bt,parentTop,true);
        pageBodyTop = genericFindViewById(R.id.page_body_top);

        showTitle = genericFindViewById(R.id.show_title);
        goneTitle = genericFindViewById(R.id.gone_title);

        showTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModuleBus.getInstance().post(IBaseClient.class,"moduleVisible","com.cangwang.page_name.PageNameExModule",true);
            }
        });

        goneTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModuleBus.getInstance().post(IBaseClient.class,"moduleVisible","com.cangwang.page_name.PageNameExModule",false);
            }
        });

        pageBodyView_bts = LayoutInflater.from(context).inflate(R.layout.page_body_bts,parentBottom,true);

        changeNameBtn = genericFindViewById(R.id.change_name);
        changeNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModuleApiManager.getInstance().getApi(PageNameApi.class).changeNameTxt("CangWang");
//                ModuleBus.getInstance().post(IBaseClient.class,"changeNameTxt","Cang_Wang");
            }
        });
    }
}
