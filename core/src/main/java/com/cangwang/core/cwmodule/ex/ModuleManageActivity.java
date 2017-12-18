package com.cangwang.core.cwmodule.ex;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import android.view.ViewGroup;

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

public abstract class ModuleManageActivity extends Activity{
    private final String TAG = "ModuleManageExActivity";
    private ViewGroup mTopViewGroup;
    private ViewGroup mBottomViewGroup;
    private ViewGroup pluginViewGroup;

    private ModuleExManager moduleManager;
    private CWModuleContext moduleContext;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_rank_layout);
        ModuleBus.getInstance().register(this);
        mTopViewGroup = (ViewGroup) findViewById(R.id.layout_top);
        mBottomViewGroup = (ViewGroup) findViewById(R.id.layout_bottom);
        pluginViewGroup = (ViewGroup) findViewById(R.id.layout_plugincenter);
        moduleManager = new ModuleExManager();
        moduleManager.moduleConfig(moduleConfig());
        initView(savedInstanceState);
    }

    public void initView(Bundle mSavedInstanceState){
        moduleContext = new CWModuleContext();
        moduleContext.setActivity(this);
        moduleContext.setSaveInstance(mSavedInstanceState);
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
                                    module.init(moduleContext, null);
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
                module.init(moduleContext, null);
                Log.d(TAG, "modulename: " + moduleIn.getClass().getCanonicalName() + " init time = " + (System.currentTimeMillis() - before) + "ms");
                moduleManager.putModule(moduleIn.getClass().getCanonicalName(), module);
            }
        }
    }


    public abstract String moduleConfig();

    @Override
    protected void onResume() {
        super.onResume();
        moduleManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        moduleManager.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        moduleManager.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        moduleManager.onDestroy();
        ModuleBus.getInstance().unregister(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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
            if (moduleManager.allModules.containsKey(moduleName))  //模块不重复添加
                return;
            CWAbsExModule module = moduleManager.getModuleByNames(moduleName);
            if (module == null){
                module = CWModuleExFactory.newModuleInstance(moduleName);
            }
            if (moduleContext !=null &&module!=null){
                boolean result = module.init(moduleContext,extend);
                if (listener!=null)
                    listener.laodResult(result);  //监听回调
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
