package com.cangwang.core.cwmodule.ex;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cangwang.core.R;
import com.cangwang.core.cwmodule.ELModuleContext;
import com.cangwang.core.info.ModuleInfo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by cangwang on 2017/6/15.
 */

public abstract class ModuleManageExFragment extends Fragment{

    private final String TAG = "ModuleManageExFragment";
    private ViewGroup mTopViewGroup;
    private ViewGroup mBottomViewGroup;
    private ViewGroup pluginViewGroup;
    private View rootView;

    private ModuleExManager moduleManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.module_rank_layout,container,false);
        mTopViewGroup = (ViewGroup) rootView.findViewById(R.id.layout_top);
        mBottomViewGroup = (ViewGroup) rootView.findViewById(R.id.layout_bottom);
        pluginViewGroup = (ViewGroup) rootView.findViewById(R.id.layout_plugincenter);
        moduleManager = new ModuleExManager();
        moduleManager.moduleConfig(moduleConfig());
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ELModuleContext modudleContext = new ELModuleContext();
        modudleContext.setActivity(getActivity());
        modudleContext.setSaveInstance(savedInstanceState);
        //关联视图
        SparseArrayCompat<ViewGroup> sVerticalViews = new SparseArrayCompat<>();
        sVerticalViews.put(ELModuleContext.TOP_VIEW_GROUP, mTopViewGroup);
        sVerticalViews.put(ELModuleContext.BOTTOM_VIEW_GROUP, mBottomViewGroup);
        sVerticalViews.put(ELModuleContext.PLUGIN_CENTER_VIEW, pluginViewGroup);
        modudleContext.setViewGroups(sVerticalViews);

        Observable.fromIterable(moduleManager.getModuleNames())
                .map(new Function<String, ModuleInfo>() {
                    @Override
                    public ModuleInfo apply(@NonNull String s){
                        return new ModuleInfo(s, ELModuleExFactory.newModuleInstance(s));
                    }
                })
//              .delay(10, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ModuleInfo>() {
                    @Override
                    public void accept(@NonNull ModuleInfo elAbsModule){
                        try {
                            if(elAbsModule!=null){
                                long before = System.currentTimeMillis();
                                elAbsModule.module.init(modudleContext, null);
                                Log.d(TAG, "modulename: " + elAbsModule.getClass().getSimpleName() + " init time = " + (System.currentTimeMillis() - before) + "ms");
                                moduleManager.putModule(elAbsModule.name, elAbsModule.module);
                            }
                        }catch (Exception ex){
                            Log.e(TAG,ex.toString());
                        }
                    }
                });
    }
    public abstract List<String> moduleConfig();

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
