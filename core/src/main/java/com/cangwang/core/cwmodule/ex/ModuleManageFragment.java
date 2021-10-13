package com.cangwang.core.cwmodule.ex;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.collection.SparseArrayCompat;
import androidx.fragment.app.Fragment;

import com.cangwang.core.IBaseClient;
import com.cangwang.core.ModuleBus;
import com.cangwang.core.ModuleCenter;
import com.cangwang.core.ModuleEvent;
import com.cangwang.core.R;
import com.cangwang.core.cwmodule.CWModuleContext;
import com.cangwang.model.ICWModule;

import java.util.List;

/**
 * Created by cangwang on 2017/6/15.
 */

public abstract class ModuleManageFragment extends Fragment {

    private final String TAG = "ModuleManageExFragment";
    private ViewGroup mTopViewGroup;
    private ViewGroup mBottomViewGroup;
    private ViewGroup pluginViewGroup;
    private View rootView;

    private ModuleExManager moduleManager;
    private CWModuleContext moduleContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.module_rank_layout,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTopViewGroup = (ViewGroup) rootView.findViewById(R.id.layout_top);
        mBottomViewGroup = (ViewGroup) rootView.findViewById(R.id.layout_bottom);
        pluginViewGroup = (ViewGroup) rootView.findViewById(R.id.layout_plugincenter);
        moduleManager = new ModuleExManager();
        moduleManager.moduleConfig(moduleConfig());
        ModuleBus.getInstance().register(this);
        moduleContext = new CWModuleContext();
        moduleContext.setActivity(getActivity());
        moduleContext.setSaveInstance(savedInstanceState);
        //关联视图
        SparseArrayCompat<ViewGroup> sVerticalViews = new SparseArrayCompat<>();
        sVerticalViews.put(CWModuleContext.TOP_VIEW_GROUP, mTopViewGroup);
        sVerticalViews.put(CWModuleContext.BOTTOM_VIEW_GROUP, mBottomViewGroup);
        sVerticalViews.put(CWModuleContext.PLUGIN_CENTER_VIEW, pluginViewGroup);
        moduleContext.setViewGroups(sVerticalViews);

        if (ModuleCenter.isFromNetWork) {  //在线加载
            for (final String moduleName : ModuleBus.getInstance().getModuleList(moduleManager.getTemplate())) {
                moduleManager.getPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        final CWAbsExModule module = CWModuleExFactory.newModuleInstance(moduleName);
                        if (module != null) {
                            moduleManager.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    long before = System.currentTimeMillis();
                                    module.onCreate(moduleContext, null);
                                    Log.d(TAG, "modulename: " + moduleName + " init time = " + (System.currentTimeMillis() - before) + "ms");
                                    moduleManager.putModule(moduleName, module);
                                }
                            });
                        }
                    }
                });
            }
        }else {   //本地缓存加载
            CWAbsExModule module;
            List<ICWModule> moduleList =CWModuleExFactory.getInstance().getTempleList(moduleManager.getTemplate());
            if (moduleList == null || moduleList.isEmpty()) return;
            for (ICWModule moduleIn : moduleList) {
                module = (CWAbsExModule) moduleIn;
                long before = System.currentTimeMillis();
                module.onCreate(moduleContext, null);
                Log.d(TAG, "modulename: " + moduleIn.getClass().getCanonicalName() + " init time = " + (System.currentTimeMillis() - before) + "ms");
                moduleManager.putModule(moduleIn.getClass().getCanonicalName(), module);
            }
        }
    }
    public abstract String moduleConfig();

    @Override
    public void onResume() {
        super.onResume();
        if (moduleManager !=null)
            moduleManager.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (moduleManager !=null)
            moduleManager.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ModuleBus.getInstance().unregister(this);
        if (moduleManager !=null)
            moduleManager.onDestroy();
        if (moduleContext !=null)
            moduleContext =null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (moduleManager !=null)
            moduleManager.onConfigurationChanged(newConfig);
    }
    /**
     * 添加模块
     * @param moduleName
     * @param extend
     */
    @ModuleEvent(coreClientClass = IBaseClient.class)
    public void addModule(String moduleName,Bundle extend){
        addModule(moduleName,extend,null);
    }

    public void addModule(String moduleName,Bundle extend,ModuleLoadListener listener){
        if (moduleName !=null && !moduleName.isEmpty()){
            if (moduleManager.allModules.containsKey(moduleName))
                return;
            CWAbsExModule module = moduleManager.getModuleByNames(moduleName);
            if (module == null){
                module = CWModuleExFactory.newModuleInstance(moduleName);
            }
            if (moduleContext !=null &&module!=null){
                boolean result = module.onCreate(moduleContext,extend);
                if (listener!=null)
                    listener.laodResult(result);
                if (result)
                    moduleManager.putModule(moduleName,module);
            }
        }
    }

    /**
     * 移除模块
     * @param moduleName
     */
    @ModuleEvent(coreClientClass = IBaseClient.class)
    public void removeModule(String moduleName){
        if (moduleName!=null &&!moduleName.isEmpty()) {
            CWAbsExModule module = moduleManager.getModuleByNames(moduleName);
            if (module != null) {
                module.detachView();  //先移除界面，再销毁
                module.onDestroy();
                moduleManager.remove(moduleName);
            }
        }
    }

    @ModuleEvent(coreClientClass = IBaseClient.class)
    public void moduleVisible(String moduleName,boolean isVisible){
        if (moduleName !=null && !moduleName.isEmpty()){
            CWAbsExModule module = moduleManager.getModuleByNames(moduleName);
            if (module !=null){
                module.setVisible(isVisible);
            }
        }
    }
}
