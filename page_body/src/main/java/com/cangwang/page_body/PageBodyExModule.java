package com.cangwang.page_body;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cangwang.core.cwmodule.ELBasicModule;
import com.cangwang.core.cwmodule.ELModuleContext;
import com.cangwang.core.util.ModuleImpl;

/**
 * Created by cangwang on 2016/12/28.
 */

public class PageBodyExModule extends ELBasicModule implements ModuleImpl{
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
        parentViewGroup_T = moduleContext.getView(ELModuleContext.TOP_VIEW_GROUP);
        parentViewGroup_B = moduleContext.getView(ELModuleContext.BOTTOM_VIEW_GROUP);
        this.moduleContext = moduleContext;
        initView();
    }

    private void initView(){
        pageBodyView_fi = LayoutInflater.from(activity).inflate(R.layout.page_body_fi,parentViewGroup_T,true);
        pageBodyTop = (TextView) pageBodyView_fi.findViewById(R.id.page_body_top);

        if (parentViewGroup_T !=null && pageBodyView_fi !=null)
            parentViewGroup_T.addView(pageBodyView_fi);

        pageBodyView_se = LayoutInflater.from(activity).inflate(R.layout.page_body_se,parentViewGroup_B,true);
        pageBodyBottom = (TextView) pageBodyView_se.findViewById(R.id.page_body_bottom);

        if (parentViewGroup_B !=null && pageBodyView_se !=null)
            parentViewGroup_B.addView(pageBodyView_se);

        changeNameBtn = (Button) pageBodyView_se.findViewById(R.id.change_page_Name);
        changeNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ModuleBus.getInstance().post(IBaseClient.class,"changeNameTxt","Cang_Wang");
                activity.startActivity(new Intent("com.cangwang.moduleFragment"));
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
