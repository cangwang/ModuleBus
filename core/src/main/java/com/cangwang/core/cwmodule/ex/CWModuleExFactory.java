package com.cangwang.core.cwmodule.ex;

/**
 * Created by cangwang on 2017/6/16.
 */

public class CWModuleExFactory {

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
}
