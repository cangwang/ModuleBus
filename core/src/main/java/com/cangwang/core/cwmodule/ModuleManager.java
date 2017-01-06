package com.cangwang.core.cwmodule;

import android.content.res.Configuration;
import android.support.v4.util.ArrayMap;


import com.cangwang.core.util.ModuleUtil;

import java.util.ArrayList;

/**
 * Created by cangwang on 2016/12/26.
 */

public class ModuleManager {
    private ArrayMap<String,ArrayList<Integer>> modules = new ArrayMap<>();
    protected ArrayMap<String,ELAbsModule> allModules = new ArrayMap<>();

    public ArrayMap<String, ArrayList<Integer>> getModules() {
        return modules;
    }

    public void setModules(ArrayMap<String, ArrayList<Integer>> modules) {
        this.modules = modules;
    }

    public void moduleConfig(ArrayMap<String, ArrayList<Integer>> modules) {
        this.modules = modules;
    }

    public ELAbsModule getModuleByNames(String name){
        if (!ModuleUtil.empty(allModules))
            return allModules.get(name);
        return null;
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
