package com.cangwang.core;

import java.util.HashMap;

/**
 *
 * Created by cangwang on 2018/1/7.
 */

public class ModuleApiManager {

    static ModuleApiManager instance = new ModuleApiManager();

    HashMap<Class<?extends MBaseApi>,MBaseApi> aMap;

    public static ModuleApiManager getInstance(){
        return instance;
    }

    private ModuleApiManager(){
        aMap = new HashMap<>();
    }

    public boolean containsApi(Class<?extends MBaseApi> clazz){
        return aMap.containsKey(clazz);
    }

    public <T extends MBaseApi> T getApi(Class<T> clazz){
        return (T) aMap.get(clazz);
    }

    public void putApi(Class<?extends MBaseApi> key,MBaseApi value){
        aMap.put(key, value);
    }

    public void removeApi(Class<?extends MBaseApi> key){
        aMap.remove(key);
    }
}
