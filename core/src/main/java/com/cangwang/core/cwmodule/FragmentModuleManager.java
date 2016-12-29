package com.cangwang.core.cwmodule;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.cangwang.core.util.ModuleUtil;

import java.util.ArrayList;

/**
 * Created by cangwang on 2016/12/26.
 */

public class FragmentModuleManager extends ModuleManager{
    private static final String TAG = "FragmentModuleManager";

    public void initModules(Bundle saveIntanceState, Activity activity, View rootView){
        //获取配置
        for(String moduleName:modules.keySet()){
            if (ModuleUtil.empty(moduleName)) return;
            Log.d(TAG,"FramentModuleManager init module name: "+ moduleName);

            ELAbsModule module = ELModuleFactory.newModuleInstance(moduleName);
            if (module!=null){
                ELModuleContext moduleContext = new ELModuleContext();
                moduleContext.setActivity(activity);
                moduleContext.setSaveInstance(saveIntanceState);

                //关联视图
                SparseArrayCompat<ViewGroup> sVerticalViews = new SparseArrayCompat<>();
                ArrayList<Integer> viewIds = modules.get(moduleName);
                if (viewIds !=null && viewIds.size() >0){
                    for (int i = 0;i<viewIds.size();i++){
                        sVerticalViews.put(i,(ViewGroup)rootView.findViewById(viewIds.get(i).intValue()));
                    }
                }

                moduleContext.setViewGroups(sVerticalViews);
                module.init(moduleContext,"");

                allModules.put(moduleName,module);
            }
        }
    }

}
