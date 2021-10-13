package com.cangwang.core.cwmodule;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.collection.ArrayMap;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

/**
 * Created by zjl on 2017/1/13.
 */

public abstract class ModuleManagerView extends View {
    private ViewModuleManager moduleManager;

    public ModuleManagerView(Context context) {
        super(context);
    }

    public ModuleManagerView(Context context, Bundle savedInstanceState, View rootView) {
        super(context);
        moduleManager = new ViewModuleManager();
        moduleManager.initModules(savedInstanceState, (FragmentActivity) context, rootView, moduleConfig());
    }

    public abstract ArrayMap<String, ArrayList<Integer>> moduleConfig();

    public void onStop() {
        if (moduleManager != null)
            moduleManager.onStop();
    }

    public void onPause() {
        if (moduleManager != null)
            moduleManager.onPause();
    }

    public void onResume() {
        if (moduleManager != null)
            moduleManager.onResume();
    }

    public void onDestroy() {
        if (moduleManager != null)
            moduleManager.onDestroy();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (moduleManager != null)
            moduleManager.onDestroy();
        super.onDetachedFromWindow();
    }

    //    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//        if (moduleManager != null)
//            moduleManager.onResume();
//    }
}
