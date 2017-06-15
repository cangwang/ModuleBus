package com.cangwang.core.cwmodule.ex;

import android.content.res.Configuration;
import android.support.v4.util.ArrayMap;

import com.cangwang.core.cwmodule.ELAbsModule;
import com.cangwang.core.util.ModuleUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cangwang on 2016/12/26.
 */

public class ModuleExManager {
    private List<String> modules = new ArrayList<>();   //模块名字
    protected ArrayMap<String,ELAbsModule> allModules = new ArrayMap<>();   //模块实体

    public List<String> getModuleNames(){
        return modules;
    }

    public void moduleConfig(List<String> modules) {
        this.modules = modules;
    }

    public ELAbsModule getModuleByNames(String name){
        if (!ModuleUtil.empty(allModules))
            return allModules.get(name);
        return null;
    }

    public void putModule(String name,ELAbsModule module){
        allModules.put(name,module);
    }

    public void onResume(){
        for (ELAbsModule module:allModules.values())
            if (module !=null)
                module.onResume();
    }

    public void onPause(){
        for (ELAbsModule module:allModules.values())
            if (module !=null)
                module.onPause();
    }

    public void onStop(){
        for (ELAbsModule module:allModules.values())
            if (module !=null)
                module.onStop();
    }

    public void onConfigurationChanged(Configuration newConfig){
        for (ELAbsModule module:allModules.values())
            if (module!=null)
                module.onOrientationChanges(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

    public void onDestroy(){
        for (ELAbsModule module:allModules.values())
            if (module !=null){
                module.onDestroy();
            }
    }
}
