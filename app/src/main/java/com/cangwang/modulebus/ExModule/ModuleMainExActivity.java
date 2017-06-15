package com.cangwang.modulebus.ExModule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.cangwang.core.cwmodule.ex.ModuleManageExActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cangwang on 2017/6/15.
 */

public class ModuleMainExActivity extends ModuleManageExActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public List<String> moduleConfig() {
//        ArrayMap<String, ArrayList<Integer>> map = new ArrayMap<>();
//        map.put(PageConfig.MODULE_PAGE_NAME,new ArrayList<Integer>(){{add(R.id.page_name);}});
//        map.put(PageConfig.MODULE_BODY_NAME,new ArrayList<Integer>(){{add(R.id.page_bodyT);add(R.id.page_bodyB);}});
        List<String> moduleList= new ArrayList<>();
        moduleList.add(PageExConfig.MODULE_PAGE_NAME);
        moduleList.add(PageExConfig.MODULE_BODY_NAME);
        return moduleList;
    }
}
