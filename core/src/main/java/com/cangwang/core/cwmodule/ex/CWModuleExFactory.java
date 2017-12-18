package com.cangwang.core.cwmodule.ex;

import com.cangwang.model.IModuleFactory;

/**
 * Created by cangwang on 2017/6/16.
 */

public class CWModuleExFactory {
    private static final String FACTORY_PATH = "com.cangwang.core.ModuleCenterFactory";

    private static IModuleFactory instance;

    public static CWAbsExModule newModuleInstance(String name){
        if (name ==null || name.equals("")){
            return null;
        }

        try{
            Class<? extends CWAbsExModule> moduleClzz = (Class<? extends CWAbsExModule>) Class.forName(name);
            if (moduleClzz !=null){
                CWAbsExModule instance = (CWAbsExModule)moduleClzz.newInstance();
                return instance;
            }
            return null;
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        return null;
    }

    public static IModuleFactory getInstance(){
        if (instance == null) {
            try {
                Class<? extends IModuleFactory> factoryClazz = (Class<? extends IModuleFactory>) Class.forName(FACTORY_PATH);
                if (factoryClazz != null) {
                    instance = factoryClazz.newInstance();
                    return instance;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }else
            return instance;
    }
}
