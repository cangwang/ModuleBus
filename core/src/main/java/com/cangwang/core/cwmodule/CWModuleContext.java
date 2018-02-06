package com.cangwang.core.cwmodule;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

/**
 * Created by cangwang on 2016/12/26.
 */

public class CWModuleContext {
    public static final int TOP_VIEW_GROUP = 0;
    public static final int BOTTOM_VIEW_GROUP = 1;
    public static final int PLUGIN_CENTER_VIEW = 2;

    private FragmentActivity context;
    private Bundle saveInstance;
    private SparseArrayCompat<ViewGroup> viewGroups = new SparseArrayCompat<>();

    public FragmentActivity getActivity(){
        return context;
    }

    public void setActivity(FragmentActivity component){
        this.context = component;
    }

    public Bundle getSaveInstance(){
        return saveInstance;
    }

    public void setSaveInstance(Bundle saveInstance){
        this.saveInstance = saveInstance;
    }

    public ViewGroup getView(int key){
        return viewGroups.get(key);
    }

    public SparseArrayCompat<ViewGroup> getViewGroups(){
        return viewGroups;
    }

    public void setViewGroups(SparseArrayCompat<ViewGroup> viewGroups){
        this.viewGroups = viewGroups;
    }
}
