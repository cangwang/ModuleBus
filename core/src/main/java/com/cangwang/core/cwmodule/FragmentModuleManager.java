package com.cangwang.core.cwmodule;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.collection.ArrayMap;
import androidx.collection.SparseArrayCompat;

import com.cangwang.core.util.ModuleUtil;

import java.util.ArrayList;

/**
 * Created by cangwang on 2016/12/26.
 */

public class FragmentModuleManager extends ModuleManager{
    private static final String TAG = "FragmentModuleManager";

    public void initModules(Bundle saveInstance, Activity activity, View rootView, ArrayMap<String,ArrayList<Integer>> modules){
        if (activity == null || modules == null) return;
        moduleConfig(modules);
        initModules(saveInstance,activity,rootView);
    }

    public void initModules(final Bundle saveIntanceState, final Activity activity, final View rootView){
        //获取配置
        for(final String moduleName:getModules().keySet()){
            if (ModuleUtil.empty(moduleName)) return;
            getPool().execute(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG,"FragmentModuleManager init module name: "+ moduleName);
                    //创建模块
                    final ELAbsModule module = ELModuleFactory.newModuleInstance(moduleName);
                    if (module!=null){
                        final ELModuleContext moduleContext = new ELModuleContext();
                        //关联Activity
                        moduleContext.setActivity(activity);
                        moduleContext.setSaveInstance(saveIntanceState);

                        //关联视图
                        SparseArrayCompat<ViewGroup> sVerticalViews = new SparseArrayCompat<>();
                        ArrayList<Integer> viewIds = getModules().get(moduleName);
                        if (viewIds !=null && viewIds.size() >0){
                            for (int i = 0;i<viewIds.size();i++){
                                sVerticalViews.put(i,(ViewGroup)rootView.findViewById(viewIds.get(i).intValue()));
                            }
                        }
                        moduleContext.setViewGroups(sVerticalViews);
                        getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                module.init(moduleContext,"");
                                allModules.put(moduleName,module);
                            }
                        });
                    }
                }
            });

        }
    }
}
