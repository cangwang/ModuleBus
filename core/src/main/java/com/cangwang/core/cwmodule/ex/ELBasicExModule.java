package com.cangwang.core.cwmodule.ex;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cangwang.core.cwmodule.ELModuleContext;

import java.util.ArrayList;
import java.util.List;

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
    public View own;
    public List<View> viewList = new ArrayList<>();

    @CallSuper
    @Override
    public boolean init(ELModuleContext moduleContext, Bundle extend) {
        context = moduleContext.getActivity();
        parentTop = moduleContext.getView(ELModuleContext.TOP_VIEW_GROUP);
        parentBottom = moduleContext.getView(ELModuleContext.BOTTOM_VIEW_GROUP);
        parentPlugin = moduleContext.getView(ELModuleContext.PLUGIN_CENTER_VIEW);
        handler = new Handler();
        return true;
    }

    public <T extends View> T genericFindViewById(int id) {
        //return返回view时,加上泛型T
        T view = (T) context.findViewById(id);
        viewList.add(view);
        return view;
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

    @CallSuper
    @Override
    public void detachView(){
        ViewGroup viewGroup;
        if (viewList!=null && viewList.size()>0) {
            viewGroup = (ViewGroup) viewList.get(0).getParent();
            if(viewGroup!=null)
                viewGroup.removeAllViewsInLayout();
        }
    }

    @CallSuper
    @Override
    public void onDestroy() {

    }

    @Override
    public void setVisible(boolean visible) {
        ViewGroup viewGroup;
        if (viewList!=null && viewList.size()>0) {
            viewGroup = (ViewGroup) viewList.get(0).getParent();
            viewGroup.setVisibility(visible?View.VISIBLE:View.GONE);
        }
    }

    public View initLayout(@LayoutRes int id, ViewGroup parentTop){
        own = LayoutInflater.from(context).inflate(id,parentTop,true);
        return own;
    }
}
