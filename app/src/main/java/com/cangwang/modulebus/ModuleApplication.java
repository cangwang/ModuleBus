package com.cangwang.modulebus;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.cangwang.core.ModuleBus;
import com.cangwang.core.util.ModuleImpl;
import com.cangwang.modulebus.ExModule.PageExConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by cangwang on 2017/2/25.
 */

public class ModuleApplication extends Application{
    private static final String TAG="ModuleApplication";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ModuleBus.init(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        long time = System.currentTimeMillis();
        for (String implName: PageExConfig.moduleCreate){
            try {
                Class<?> clazz = Class.forName(implName);
                if (clazz.newInstance() instanceof ModuleImpl){
                    ModuleImpl impl = (ModuleImpl) clazz.newInstance();
                    impl.onLoad(this);
                }
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }catch (InstantiationException e){
                e.printStackTrace();
            }
        }
        Log.v(TAG,"interface load time = " +(System.currentTimeMillis()-time));
    }
}
