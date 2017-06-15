package com.cangwang.core.info;

import com.cangwang.core.cwmodule.ELAbsModule;

/**
 * Created by zjl on 2017/5/18.
 */

public class ModuleInfo {
    public String name;
    public ELAbsModule module;

    public ModuleInfo(String name, ELAbsModule module) {
        this.name = name;
        this.module = module;
    }
}
