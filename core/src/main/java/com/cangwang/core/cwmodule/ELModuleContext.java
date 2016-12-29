package com.cangwang.core.cwmodule;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

/**
 * Created by cangwang on 2016/12/26.
 */

public class ELModuleContext {
    private Activity component;
    private Bundle saveInstance;
    private SparseArrayCompat<ViewGroup> viewGroups = new SparseArrayCompat<>();

    public Activity getActivity(){
        return component;
    }

    public void setActivity(Activity component){
        this.component = component;
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
