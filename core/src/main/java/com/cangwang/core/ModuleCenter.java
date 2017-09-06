package com.cangwang.core;

import android.content.Context;
import android.util.Log;

import com.cangwang.core.template.IModuleUnit;
import com.cangwang.core.util.ModuleUtil;
import com.cangwang.core.util.ClassUtils;
import com.cangwang.model.ModuleMeta;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cangwang on 2017/9/4.
 */

public class ModuleCenter {
    private final static String TAG = "ModuleCenter";

    private static Set<ModuleMeta> group= new HashSet<>();
    private static Map<String,Set<ModuleMeta>> sortgroup = new HashMap<>();

    public synchronized static void init(Context context){
        try {
            List<String> classFileNames = ClassUtils.getFileNameByPackageName(context, ModuleUtil.NAME_OF_MODULEUNIT);
            for (String className:classFileNames){
                if (className.startsWith(ModuleUtil.ADDRESS_OF_MODULEUNIT)){
                    IModuleUnit iModuleUnit = (IModuleUnit)(Class.forName(className).getConstructor().newInstance());
                    iModuleUnit.loadInto(group);
                }
            }
            Log.i(TAG,"group ="+group.toString());
            sort(group);

        }catch (Exception e){
            Log.e(TAG,e.toString());
        }
    }

    private static void sort(Set<ModuleMeta> group){
        for (ModuleMeta meta:group){
            Log.i(TAG,"meta ="+meta.toString());
            String[] templets = split(meta.templet);
            for (String templet :templets) {
                Set<ModuleMeta> metaSet = new HashSet<>();
                if (sortgroup.get(templet) != null) {
                    metaSet = sortgroup.get(templet);
                }
                metaSet.add(meta);
                sortgroup.put(templet, metaSet);
            }
        }
    }

    private static String[] split(String groupName){
        return groupName.split(",");
    }

    public static List<String> getModuleList(String templet){
        List<String> list = new ArrayList<>();
        if (sortgroup.containsKey(templet)) {
            Set<ModuleMeta> metaSet = sortgroup.get(templet);
            for (ModuleMeta meta : metaSet) {
                list.add(meta.moduleName);
            }
        }else {
            Log.i(TAG,"do not have key "+ templet);
        }
        Log.i(TAG,"list ="+list.toString());
        return list;
    }
}
