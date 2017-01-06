package com.cangwang.modulebus;

import android.support.v4.util.ArrayMap;

import com.cangwang.core.cwmodule.ModuleManageFragment;

import java.util.ArrayList;

/**
 * Created by air on 2017/1/3.
 */

public class ModuleExampleFragment extends ModuleManageFragment {

    @Override
    public int getContentViewId() {
        return R.layout.fragment_module;
    }

    @Override
    public ArrayMap<String, ArrayList<Integer>> moduleConfig() {
        ArrayMap<String, ArrayList<Integer>> map = new ArrayMap<>();
        map.put(PageConfig.MODULE_PAGE_NAME,new ArrayList<Integer>(){{add(R.id.page_name);}});
        map.put(PageConfig.MODULE_BODY_BT_NAME,new ArrayList<Integer>(){{add(R.id.page_bodyT);add(R.id.page_bodyB);}});
        return map;
    }
}
