package com.cangwang.modulebus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import com.cangwang.core.cwmodule.ModuleManageActivity;

import java.util.ArrayList;

/**
 * Created by air on 2016/12/28.
 */

public class ModuleMainActivity extends ModuleManageActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main_module;
    }


    @Override
    public ArrayMap<String, ArrayList<Integer>> moduleConfig() {
        ArrayMap<String, ArrayList<Integer>> map = new ArrayMap<>();
        map.put(PageConfig.MODULE_PAGE_NAME,new ArrayList<Integer>(){{add(R.id.page_name);}});
        map.put(PageConfig.MODULE_BODY_NAME,new ArrayList<Integer>(){{add(R.id.page_bodyT);add(R.id.page_bodyB);}});
        return map;
    }
}
