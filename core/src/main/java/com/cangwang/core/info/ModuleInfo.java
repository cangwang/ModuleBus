package com.cangwang.core.info;


import com.cangwang.core.cwmodule.ex.ELAbsExModule;

/**
 * Created by zjl on 2017/5/18.
 */

public class ModuleInfo {
    public String name;
    public ELAbsExModule module;

    public ModuleInfo(String name, ELAbsExModule module) {
        this.name = name;
        this.module = module;
    }
}
