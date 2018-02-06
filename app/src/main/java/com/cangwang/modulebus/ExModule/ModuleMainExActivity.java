package com.cangwang.modulebus.ExModule;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cangwang.core.cwmodule.ex.ModuleManageExActivity;
import com.cangwang.modulebus.R;

/**
 * Created by cangwang on 2017/6/15.
 */

public class ModuleMainExActivity extends ModuleManageExActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackGroundResouce(R.color.black);
    }

    //    @Override
//    public List<String> moduleConfig() {
//        return ModuleBus.getInstance().getModuleList("top");
//    }

    @Override
    public String moduleConfig() {
        return "top";
    }
}
