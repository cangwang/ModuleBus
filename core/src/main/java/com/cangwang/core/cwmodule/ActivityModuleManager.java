package com.cangwang.core.cwmodule;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.view.ViewGroup;


import androidx.collection.ArrayMap;
import androidx.collection.SparseArrayCompat;

import java.util.ArrayList;

/**
 * Created by cangwang on 2016/12/26.
 */

public class ActivityModuleManager extends ModuleManager{
    private static final String TAG = "ActivityModuleManager";

    public void initModules(Bundle saveInstance, Activity activity, ArrayMap<String,ArrayList<Integer>> modules){
        if (activity == null || modules == null) return;
        moduleConfig(modules);
        initModules(saveInstance,activity);
    }

    public void initModules(final Bundle saveInstance, final Activity activity){
        if (getModules() ==null) return;

        //获取配置
        for(final String moduleName: getModules().keySet()){
            getPool().execute(new Runnable() {
                @Override
                public void run() {
                    final ELAbsModule module = ELModuleFactory.newModuleInstance(moduleName);
                    Log.d(TAG,"ActivityModuleManager init module name: "+ moduleName);
                    if (module!=null) {
                        final ELModuleContext moduleContext =new ELModuleContext();
                        moduleContext.setActivity(activity);
                        moduleContext.setSaveInstance(saveInstance);

                        //关联视图
                        SparseArrayCompat<ViewGroup> viewGroups =new SparseArrayCompat<>();
                        ArrayList<Integer> mViewIds = getModules().get(moduleName);
                        if (mViewIds !=null &&mViewIds.size()>0) {
                            for (int i = 0; i < mViewIds.size(); i++) {
                                viewGroups.put(i, (ViewGroup) activity.findViewById(mViewIds.get(i).intValue()));
                            }
                        }

                        moduleContext.setViewGroups(viewGroups);

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

//    public void initModules(Bundle saveInstance, Activity activity) {
//        if (getModules() ==null) return;
//
//        //获取配置
//         for(String moduleName: getModules().keySet()) {
//             ELAbsModule module = ELModuleFactory.newModuleInstance(moduleName);
//             Log.d(TAG, "ActivityModuleManager init module name: " + moduleName);
//
//             if (module != null) {
//                 ELModuleContext moduleContext = new ELModuleContext();
//                 moduleContext.setActivity(activity);
//                 moduleContext.setSaveInstance(saveInstance);
//
//                 //关联视图
//                 SparseArrayCompat<ViewGroup> viewGroups = new SparseArrayCompat<>();
//                 ArrayList<Integer> mViewIds = getModules().get(moduleName);
//                 if (mViewIds != null && mViewIds.size() > 0) {
//                     for (int i = 0; i < mViewIds.size(); i++) {
//                         viewGroups.put(i, (ViewGroup) activity.findViewById(mViewIds.get(i).intValue()));
//                     }
//                     moduleContext.setViewGroups(viewGroups);
//                     module.init(moduleContext, "");
//                 }
//             }
//             allModules.put(moduleName,module);
//         }
//    }

}
