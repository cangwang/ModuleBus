package com.cangwang.core.cwmodule;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * Created by cangwang on 2016/12/26.
 */

public abstract class ModuleManageFragment extends Fragment {
    private FragmentModuleManager moduleManager;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moduleManager = new FragmentModuleManager();
        moduleManager.initModules(savedInstanceState,getActivity(),view,moduleConfig());
    }

    public abstract ArrayMap<String,ArrayList<Integer>> moduleConfig();

    @Override
    public void onResume() {
        super.onResume();
        if (moduleManager !=null)
            moduleManager.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (moduleManager !=null)
            moduleManager.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (moduleManager !=null)
            moduleManager.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (moduleManager !=null)
            moduleManager.onConfigurationChanged(newConfig);
    }
}
