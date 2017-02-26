package com.cangwang.modulebus;

import android.app.Application;
import android.util.Log;

import com.cangwang.core.util.ModuleImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by cangwang on 2017/2/25.
 */

public class ModuleApplication extends Application{
    private static final String TAG="ModuleApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        long time = System.currentTimeMillis();
//        for (String implName:PageConfig.moduleCreate){
//            try {
//                Class<?> clazz = Class.forName(implName);
//                Object impl = clazz.newInstance();
//                if (clazz.newInstance() instanceof ModuleImpl){
//                    Method m = clazz.getDeclaredMethod("onLoad",Application.class);
//                    m.invoke(impl,this);
//                }
//            }catch (ClassNotFoundException e){
//                e.printStackTrace();
//            }catch (IllegalAccessException e){
//                e.printStackTrace();
//            }catch (InstantiationException e){
//                e.printStackTrace();
//            }catch (NoSuchMethodException e){
//                e.printStackTrace();
//            }catch (InvocationTargetException e){
//                e.printStackTrace();
//            }
//        }
//        Log.v(TAG,"invoke load time = " +(System.currentTimeMillis()-time));
//        time=System.currentTimeMillis();
        for (String implName:PageConfig.moduleCreate){
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
