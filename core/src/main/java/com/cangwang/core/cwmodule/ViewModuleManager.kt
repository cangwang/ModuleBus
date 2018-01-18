//package com.cangwang.core.cwmodule;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.support.v4.util.ArrayMap;
//import android.support.v4.util.SparseArrayCompat;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.cangwang.core.cwmodule.ex.CWAbsExModule;
//import com.cangwang.core.cwmodule.ex.CWBasicExModule;
//import com.cangwang.core.cwmodule.ex.CWModuleExFactory;
//import com.cangwang.core.cwmodule.ex.ModuleExManager;
//import com.cangwang.core.util.ModuleUtil;
//
//import java.util.ArrayList;
//
///**
// * ModuleMaanger for View
// * Created by zjl on 2017/1/13.
// */
//
//public class ViewModuleManager extends ModuleExManager{
//    private static final String TAG = "ViewModuleManager";
//
//    public void initModules(Bundle saveInstance, Activity activity, View rootView, ArrayMap<String,ArrayList<Integer>> modules){
//        if (activity == null || modules == null) return;
//        moduleConfig(modules);
//        initModules(saveInstance,activity,rootView);
//    }
//
//    public void initModules(Bundle saveIntanceState, Activity activity, View rootView){
//        //获取配置
//        for(String moduleName:getModules().keySet()){
//            if (ModuleUtil.empty(moduleName)) return;
//            Log.d(TAG,"ViewModuleManager init module name: "+ moduleName);
//
//            CWAbsExModule module = CWModuleExFactory.newModuleInstance(moduleName);
//            if (module!=null){
//                CWModuleContext moduleContext = new CWModuleContext();
//                moduleContext.setActivity(activity);
//                moduleContext.setSaveInstance(saveIntanceState);
//
//                //关联视图
//                SparseArrayCompat<ViewGroup> sVerticalViews = new SparseArrayCompat<>();
//                ArrayList<Integer> viewIds = getModules().get(moduleName);
//                if (viewIds !=null && viewIds.size() >0){
//                    for (int i = 0;i<viewIds.size();i++){
//                        sVerticalViews.put(i,(ViewGroup)rootView.findViewById(viewIds.get(i).intValue()));
//                    }
//                }
//
//                moduleContext.setViewGroups(sVerticalViews);
//                module.init(moduleContext,"");
//
//                allModules.put(moduleName,module);
//            }
//        }
//    }
//}
