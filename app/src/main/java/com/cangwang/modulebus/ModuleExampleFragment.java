package com.cangwang.modulebus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cangwang.core.cwmodule.ModuleManageFragment;
import com.cangwang.core.cwmodule.ModuleManagerView;

import java.util.ArrayList;

/**
 * Created by zjl on 2017/1/3.
 */

public class ModuleExampleFragment extends ModuleManageFragment {

    private ModuleManagerView moduleManagerView;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        moduleManagerView = new ModuleManagerView(getActivity(),savedInstanceState,view.findViewById(R.id.page_view)) {
            @Override
            public ArrayMap<String, ArrayList<Integer>> moduleConfig() {
                ArrayMap<String, ArrayList<Integer>> map = new ArrayMap<>();
                map.put(PageConfig.MODULE_VIEW_PAGE_NAME,new ArrayList<Integer>(){{add(R.id.page_view);}});
                return map;
            }
        };
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (moduleManagerView !=null)
            moduleManagerView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (moduleManagerView !=null)
            moduleManagerView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (moduleManagerView !=null)
            moduleManagerView.onResume();
    }

//    @Override
//    public void onDestroy() {
//        if (moduleManagerView !=null)
//            moduleManagerView.onDestroy();
//        super.onDestroy();
//    }
}
