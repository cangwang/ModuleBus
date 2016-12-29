package com.cangwang.core.cwmodule;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by cangwang on 2016/12/26.
 */

public abstract class ModuleManageActivity extends AppCompatActivity{

    private ActivityModuleManager moduleManager;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        moduleManager = new ActivityModuleManager();
        moduleManager.initModules(savedInstanceState,this,moduleConfig());
    }

    @LayoutRes
    public abstract int getContentViewId();

    public abstract ArrayMap<String,ArrayList<Integer>> moduleConfig();

    @Override
    protected void onResume() {
        super.onResume();
        moduleManager.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        moduleManager.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        moduleManager.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
