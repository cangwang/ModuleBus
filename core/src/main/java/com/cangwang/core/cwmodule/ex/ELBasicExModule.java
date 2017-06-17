package com.cangwang.core.cwmodule.ex;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;

import com.cangwang.core.cwmodule.ELAbsModule;
import com.cangwang.core.cwmodule.ELModuleContext;

/**
 * Created by cangwang on 2016/12/26.
 */

public class ELBasicExModule extends ELAbsExModule{
    public Activity context;
    public FragmentActivity mContext;
    public ELModuleContext moduleContext;
    public Handler handler;
    public ViewGroup parentTop;
    public ViewGroup parentBottom;
    public ViewGroup parentPlugin;

    @Override
    public boolean init(ELModuleContext moduleContext, Bundle extend) {
        context = moduleContext.getActivity();
        parentTop = moduleContext.getView(ELModuleContext.TOP_VIEW_GROUP);
        parentBottom = moduleContext.getView(ELModuleContext.BOTTOM_VIEW_GROUP);
        parentPlugin = moduleContext.getView(ELModuleContext.PLUGIN_CENTER_VIEW);
        handler = new Handler();
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onOrientationChanges(boolean isLandscape) {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }
}
