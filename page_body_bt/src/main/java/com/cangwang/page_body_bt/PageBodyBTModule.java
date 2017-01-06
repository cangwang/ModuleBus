package com.cangwang.page_body_bt;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cangwang.core.IBaseClient;
import com.cangwang.core.ModuleBus;
import com.cangwang.core.cwmodule.ELBasicModule;
import com.cangwang.core.cwmodule.ELModuleContext;

/**
 * Created by air on 2016/12/28.
 */

public class PageBodyBTModule extends ELBasicModule {
    private Activity activity;
    private ViewGroup parentViewGroup_BT;
    private ViewGroup parentViewGroup_B;
    private View pageBodyView_bt;
    private View pageBodyView_bts;
    private TextView pageBodyTop;
    private TextView pageBodyBottom;
    private Button changeNameBtn;

    @Override
    public void init(ELModuleContext moduleContext, String extend) {
        super.init(moduleContext, extend);
        activity = moduleContext.getActivity();
        parentViewGroup_BT = moduleContext.getView(0);
        parentViewGroup_B = moduleContext.getView(1);
        this.moduleContext = moduleContext;
        initView();
    }

    private void initView(){
        pageBodyView_bt = LayoutInflater.from(activity).inflate(R.layout.page_body_bt,parentViewGroup_BT,true);
        pageBodyTop = (TextView) pageBodyView_bt.findViewById(R.id.page_body_top);

        pageBodyView_bts = LayoutInflater.from(activity).inflate(R.layout.page_body_bts,parentViewGroup_B,true);

        changeNameBtn = (Button) pageBodyView_bts.findViewById(R.id.change_name);
        changeNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModuleBus.getInstance().post(IBaseClient.class,"changeNameTxt","Cang_Wang");
            }
        });
    }
}
