package com.cangwang.core.cwmodule;

/**
 * Created by cangwang on 2016/12/26.
 */

public class ELModuleFactory {

    public static ELAbsModule newModuleInstance(String name){
        if (name ==null || name.equals("")){
            return null;
        }

        try{
            Class<? extends ELAbsModule> moduleClzz = (Class<? extends ELAbsModule>) Class.forName(name);
            if (moduleClzz !=null){
                ELAbsModule instance = (ELAbsModule)moduleClzz.newInstance();
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
