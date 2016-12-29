package com.cangwang.page_body;

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

/**
 * Created by air on 2016/12/28.
 */

public class PageBodyModule extends ELBasicModule {
    private Activity activity;
    private ViewGroup parentViewGroup_T;
    private ViewGroup parentViewGroup_B;
    private View pageBodyView_fi;
    private View pageBodyView_se;
    private TextView pageBodyTop;
    private TextView pageBodyBottom;
    private Button changeNameBtn;

    @Override
    public void init(ELModuleContext moduleContext, String extend) {
        super.init(moduleContext, extend);
        activity = moduleContext.getActivity();
        parentViewGroup_T = moduleContext.getView(0);
        parentViewGroup_B = moduleContext.getView(1);
        this.moduleContext = moduleContext;
        initView();
    }

    private void initView(){
        pageBodyView_fi = LayoutInflater.from(activity).inflate(R.layout.page_body_fi,parentViewGroup_T,true);
        pageBodyTop = (TextView) pageBodyView_fi.findViewById(R.id.page_body_top);

        pageBodyView_se = LayoutInflater.from(activity).inflate(R.layout.page_body_se,parentViewGroup_B,true);
        pageBodyBottom = (TextView) pageBodyView_se.findViewById(R.id.page_body_bottom);
        changeNameBtn = (Button) pageBodyView_se.findViewById(R.id.change_page_Name);
        changeNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModuleBus.getInstance().post(IBaseClient.class,"changeNameTxt","Cang_Wang");
            }
        });
    }
}
