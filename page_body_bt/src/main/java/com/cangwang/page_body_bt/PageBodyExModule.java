package com.cangwang.page_body_bt;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cangwang.core.IBaseClient;
import com.cangwang.core.ModuleBus;
import com.cangwang.core.cwmodule.ELBasicModule;
import com.cangwang.core.cwmodule.ELModuleContext;
import com.cangwang.core.cwmodule.ex.ELBasicExModule;

/**
 * Created by cangwang on 2017/6/15.
 */

public class PageBodyExModule extends ELBasicExModule {
    private View pageBodyView_bt;
    private View pageBodyView_bts;
    private TextView pageBodyTop;
    private TextView pageBodyBottom;
    private Button changeNameBtn;

    @Override
    public void init(ELModuleContext moduleContext, String extend) {
        super.init(moduleContext, extend);
        this.moduleContext = moduleContext;
        initView();
    }

    private void initView(){
        pageBodyView_bt = LayoutInflater.from(context).inflate(R.layout.page_body_bt,parentTop,true);
        pageBodyTop = (TextView) pageBodyView_bt.findViewById(R.id.page_body_top);

        pageBodyView_bts = LayoutInflater.from(context).inflate(R.layout.page_body_bts,parentBottom,true);

        changeNameBtn = (Button) pageBodyView_bts.findViewById(R.id.change_name);
        changeNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModuleBus.getInstance().post(IBaseClient.class,"changeNameTxt","Cang_Wang");
            }
        });
    }
}
