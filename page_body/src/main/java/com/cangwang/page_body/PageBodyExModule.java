package com.cangwang.page_body;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.cangwang.core.cwmodule.ELModuleContext;
import com.cangwang.core.cwmodule.ex.ELBasicExModule;
import com.cangwang.core.util.ModuleImpl;

/**
 * Created by cangwang on 2016/12/28.
 */

public class PageBodyExModule extends ELBasicExModule implements ModuleImpl{
    private View pageBodyView_fi;
    private View pageBodyView_se;
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
        pageBodyView_fi = LayoutInflater.from(context).inflate(R.layout.page_body_fi,parentTop,true);
        pageBodyTop = (TextView) pageBodyView_fi.findViewById(R.id.page_body_top);

        pageBodyView_se = LayoutInflater.from(context).inflate(R.layout.page_body_se,parentBottom,true);
        pageBodyBottom = (TextView) pageBodyView_se.findViewById(R.id.page_body_bottom);

        changeNameBtn = (Button) pageBodyView_se.findViewById(R.id.change_page_Name);
        changeNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ModuleBus.getInstance().post(IBaseClient.class,"changeNameTxt","Cang_Wang");
                context.startActivity(new Intent("com.cangwang.moduleExFg"));
            }
        });
    }

    @Override
    public void onLoad(Application app) {
        for (int i=0;i<5;i++){
            Log.v("PageBodyModule","PageBodyModule onLoad");
        }
    }
}
