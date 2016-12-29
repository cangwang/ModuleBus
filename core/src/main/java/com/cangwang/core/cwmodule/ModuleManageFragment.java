package com.cangwang.core.cwmodule;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cangwang on 2016/12/26.
 */

public abstract class ModuleManageFragment extends Fragment{
    private View rootView;
    private FragmentModuleManager moduleManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =  inflater.inflate(getContentViewId(),container,false);
        moduleManager = new FragmentModuleManager();
        moduleManager.initModules(savedInstanceState,getActivity(),rootView);
        return rootView;
    }



    @Override
    public void onResume() {
        super.onResume();
        moduleManager.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        moduleManager.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        moduleManager.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        moduleManager.onConfigurationChanged(newConfig);
    }



    public abstract int getContentViewId();
}
