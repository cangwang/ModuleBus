package com.cangwang.core.cwmodule;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;

import java.util.ArrayList;

/**
 * Created by cangwang on 2016/12/26.
 */

public abstract class ModuleManageActivity extends AppCompatActivity{

    private ActivityModuleManager moduleManager;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().getRootView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (moduleManager==null) {
                    long ti = System.currentTimeMillis();
                    moduleManager = new ActivityModuleManager();
                    moduleManager.initModules(savedInstanceState, ModuleManageActivity.this, moduleConfig());
                    Log.v("ModuleManageActivity", "init use time = " + (System.currentTimeMillis() - ti));
                }
            }
        });

    }

    public abstract ArrayMap<String,ArrayList<Integer>> moduleConfig();

    @Override
    protected void onResume() {
        super.onResume();
        if(moduleManager !=null)
            moduleManager.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(moduleManager !=null)
            moduleManager.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(moduleManager !=null)
            moduleManager.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(moduleManager !=null)
            moduleManager.onConfigurationChanged(newConfig);
    }
}
