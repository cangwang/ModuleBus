package com.cangwang.core.info;


import com.cangwang.core.cwmodule.ex.CWAbsExModule;

/**
 * Created by zjl on 2017/5/18.
 */

public class ModuleInfo {
    public String name;
    public CWAbsExModule module;

    public ModuleInfo(String name, CWAbsExModule module) {
        this.name = name;
        this.module = module;
    }
}
