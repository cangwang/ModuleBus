package com.cangwang.core.cwmodule.ex;

import android.content.res.Configuration;
import android.os.Handler;
import android.support.v4.util.ArrayMap;

import com.cangwang.core.util.ModuleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cangwang on 2016/12/26.
 */

public class ModuleExManager {
    private List<String> modules = new ArrayList<>();   //模块名字
    protected ArrayMap<String,CWAbsExModule> allModules = new ArrayMap<>();   //模块实体

    public List<String> getModuleNames(){
        return modules;
    }

    public void moduleConfig(List<String> modules) {
        this.modules = modules;
    }

    private Handler handler;
    private ExecutorService pool;

    public Handler getHandler(){
        if (handler == null){
            handler = new Handler();
        }
        return handler;
    }

    public ExecutorService getPool(){
        if (pool ==null){
            pool = Executors.newSingleThreadExecutor();
        }
        return pool;
    }

    public CWAbsExModule getModuleByNames(String name){
        if (!ModuleUtil.empty(allModules))
            return allModules.get(name);
        return null;
    }

    public void remove(String name){
        if (!ModuleUtil.empty(allModules)){
            allModules.remove(name);
        }
    }

    public void putModule(String name,CWAbsExModule module){
        allModules.put(name,module);
    }

    public void onResume(){
        for (CWAbsExModule module:allModules.values())
            if (module !=null)
                module.onResume();
    }

    public void onPause(){
        for (CWAbsExModule module:allModules.values())
            if (module !=null)
                module.onPause();
    }

    public void onStop(){
        for (CWAbsExModule module:allModules.values())
            if (module !=null)
                module.onStop();
    }

    public void onConfigurationChanged(Configuration newConfig){
        for (CWAbsExModule module:allModules.values())
            if (module!=null)
                module.onOrientationChanges(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

    public void onDestroy(){
        handler = null;
        pool=null;
        for (CWAbsExModule module:allModules.values()) {
            if (module != null) {
                module.onDestroy();
            }
        }
    }
}
