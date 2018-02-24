package com.cangwang.core.cwmodule.ex;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cangwang.core.MBaseApi;
import com.cangwang.core.ModuleApiManager;
import com.cangwang.core.cwmodule.CWModuleContext;
import com.cangwang.core.cwmodule.api.BackPressStack;
import com.cangwang.core.cwmodule.api.ModuleBackpress;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by cangwang on 2016/12/26.
 */

public class CWBasicExModule extends CWAbsExModule {
    public FragmentActivity context;
    public CWModuleContext moduleContext;
    public Handler handler;
    protected ViewGroup parentTop;
    protected ViewGroup parentBottom;
    protected ViewGroup parentPlugin;
    private View rootView;
    private boolean isShow=false;
    private List<View> viewList;
    private LayoutInflater inflater;
    private Stack<ModuleBackpress> stack;
    public String templateName;

    @CallSuper
    @Override
    public boolean onCreate(CWModuleContext moduleContext, Bundle extend) {
        this.moduleContext = moduleContext;
        context = moduleContext.getActivity();
        inflater = moduleContext.getInflater();
        parentTop = moduleContext.getView(CWModuleContext.TOP_VIEW_GROUP);
        parentBottom = moduleContext.getView(CWModuleContext.BOTTOM_VIEW_GROUP);
        parentPlugin = moduleContext.getView(CWModuleContext.PLUGIN_CENTER_VIEW);
        handler = new Handler();
        templateName = moduleContext.getTemplateName();
        viewList = new ArrayList<>();
        stack = BackPressStack.getInstance().getStack();
        return true;
    }

    public void setContentView(@LayoutRes int layoutResID){
        setContentView(layoutResID,parentPlugin);
    }

    public void setContentView(@LayoutRes int layoutResID,ViewGroup viewGroup){
        setContentView(layoutResID,viewGroup,false);
    }

    public void setContentView(@LayoutRes int layoutResID,ViewGroup viewGroup, boolean attachToRoot){
        rootView = inflater.inflate(layoutResID,viewGroup,attachToRoot);
        if (rootView!=null && viewGroup !=null)
            viewGroup.addView(rootView);
        isShow = true;
        if (this instanceof ModuleBackpress){
            stack.push((ModuleBackpress) this);
            Log.e("BasicExModule","push "+BackPressStack.getInstance().getStack().toString());
        }
    }

    public <T extends View> T findViewById(int id) {
        //return返回view时,加上泛型T
        if (context ==null) return null;
        T view = (T) context.findViewById(id);
        if (viewList!=null && view !=null)
            viewList.add(view);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onStart() {

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

    public Resources getResources() {
        return context.getResources();
    }

    public Activity getActivity(){
        return context;
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
        context =null;
        moduleContext =null;
        handler =null;
        parentTop = null;
        parentBottom = null;
        parentPlugin =null;
        rootView = null;
        viewList =null;
        isShow =false;
        if (this instanceof ModuleBackpress && stack.contains(this)){
            stack.remove(this);
        }
    }

    @Override
    public void setVisible(boolean visible) {
        ViewGroup viewGroup;
        if (viewList!=null && viewList.size()>0) {
            viewGroup = (ViewGroup) viewList.get(0).getParent();
            viewGroup.setVisibility(visible?View.VISIBLE:View.GONE);
        }
        setRootVisbile(visible?View.VISIBLE:View.GONE);
    }

    private void setRootVisbile(int visbile){
        isShow = visbile == View.VISIBLE;
        if (this instanceof ModuleBackpress) {
            if (isShow && !stack.contains(this)) {
                stack.push((ModuleBackpress) this);
                Log.e("BasicExModule","push "+BackPressStack.getInstance().getStack().toString());
            }else if (!isShow && stack.contains(this)){
                Log.e("BasicExModule","pop "+BackPressStack.getInstance().getStack().toString());
                stack.pop();
            }
        }
    }

    public boolean isVisible(){
        return isShow;
    }

    public void showModule() {
        if(rootView !=null)
            rootView.setVisibility(View.VISIBLE);
        setRootVisbile(View.VISIBLE);
    }

    public void hideModule(){
        if(rootView !=null)
            rootView.setVisibility(View.GONE);
        if (BackPressStack.getInstance().getStack().contains(this)){
            BackPressStack.getInstance().getStack().pop();
        }
        setRootVisbile(View.GONE);
    }

    @Override
    public void registerMApi(Class<? extends MBaseApi> key, MBaseApi value) {
        ModuleApiManager.getInstance().putApi(key,value);
    }

    @Override
    public void unregisterMApi(Class<? extends MBaseApi> key) {
        ModuleApiManager.getInstance().removeApi(key);
    }

    public void setOnClickListener(View.OnClickListener listener){
        rootView.setOnClickListener(listener);
    }

    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();
        /**
         * 获取Android设备中所有正在运行的App
         */
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
