package com.cangwang.core.cwmodule.ex;

/**
 * Created by cangwang on 2017/6/16.
 */

public class ELModuleExFactory {

    public static ELAbsExModule newModuleInstance(String name){
        if (name ==null || name.equals("")){
            return null;
        }

        try{
            Class<? extends ELAbsExModule> moduleClzz = (Class<? extends ELAbsExModule>) Class.forName(name);
            if (moduleClzz !=null){
                ELAbsExModule instance = (ELAbsExModule)moduleClzz.newInstance();
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
