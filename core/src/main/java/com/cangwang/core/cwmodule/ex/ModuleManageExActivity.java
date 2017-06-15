package com.cangwang.core.cwmodule.ex;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

import com.cangwang.core.R;
import com.cangwang.core.cwmodule.ELModuleContext;
import com.cangwang.core.cwmodule.ELModuleFactory;
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

public abstract class ModuleManageExActivity extends AppCompatActivity{
    private final String TAG = "ModuleManageExActivity";
    private ViewGroup mTopViewGroup;
    private ViewGroup mBottomViewGroup;
    private ViewGroup pluginViewGroup;

    private ModuleExManager moduleManager;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_rank_layout);
        mTopViewGroup = (ViewGroup) findViewById(R.id.layout_top);
        mBottomViewGroup = (ViewGroup) findViewById(R.id.layout_bottom);
        pluginViewGroup = (ViewGroup) findViewById(R.id.layout_plugincenter);
        moduleManager = new ModuleExManager();

        moduleManager.moduleConfig(moduleConfig());
        initView(savedInstanceState);
    }

    public void initView(Bundle mSavedInstanceState){
        final ELModuleContext modudleContext = new ELModuleContext();
        modudleContext.setActivity(this);
        modudleContext.setSaveInstance(mSavedInstanceState);
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
                        return new ModuleInfo(s, ELModuleFactory.newModuleInstance(s));
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
                                elAbsModule.module.init(modudleContext, "");
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
    protected void onResume() {
        super.onResume();
        moduleManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        moduleManager.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        moduleManager.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        moduleManager.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        moduleManager.onConfigurationChanged(newConfig);
    }
}
