package com.cangwang.modulebus.ExModule

import android.os.Bundle

import com.cangwang.core.ModuleBus
import com.cangwang.core.cwmodule.ex.ModuleManageExActivity

/**
 * Created by cangwang on 2017/6/15.
 */

class ModuleMainExActivity : ModuleManageExActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //    @Override
    //    public List<String> moduleConfig() {
    //        return ModuleBus.getInstance().getModuleList("top");
    //    }

    override fun moduleConfig(): String {
        return "top"
    }
}
