package com.cangwang.core;

import android.content.Context;
import android.util.Log;

import com.cangwang.annotation.ModuleUnit;
import com.cangwang.bean.ModuleUnitBean;
import com.cangwang.core.template.IModuleUnit;
import com.cangwang.core.util.ModuleUtil;
import com.cangwang.core.util.ClassUtils;
import com.cangwang.model.ModuleMeta;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Module loading
 * Created by cangwang on 2017/9/4.
 */

public class ModuleCenter {
    private final static String TAG = "ModuleCenter";

    private static List<ModuleMeta> group= new ArrayList<>();
    private static Map<String,List<ModuleMeta>> sortgroup = new HashMap<>();
    private static List<ModuleUnitBean> moduleGroup = new ArrayList<>();
    private static Map<String ,List<ModuleUnitBean>> templetList = new HashMap<>();
    public static boolean isFromNetWork =false;

    public synchronized static void init(Context context){
//        JSONArray array = ModuleUtil.getAssetJson(context,"center.json");
//        if (array == null) return;
//        Log.e(TAG,"modulearray = "+array.toString());
//        int length = array.length();
//        try {
//            for (int i = 0;i<length;i++){
//                JSONObject o = array.getJSONObject(i);
//                ModuleUnitBean bean = new ModuleUnitBean(o.getString("path"),
//                        o.getString("templet"),
//                        o.getString("title"),
//                        o.getInt("layout_level"),
//                        o.getInt("extra_level"));
//                moduleGroup.add(bean);
//            }
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
        init(context,ModuleUtil.getAssetJsonObject(context,"center.json"),false);
    }

    public synchronized static void init(Context context,JSONObject object,boolean isFromNet){
        isFromNetWork = isFromNet;
        if (object == null) return;
        Log.e(TAG,"templet = "+object.toString());
        try {
            Iterator iterator = object.keys();
            while (iterator.hasNext()){
                String key = iterator.next().toString();
                JSONArray array = object.getJSONArray(key);
                List<ModuleUnitBean> list = new ArrayList<>();
                int length = array.length();
                for (int i = 0;i < length;i++){
                    JSONObject o = array.getJSONObject(i);
                    ModuleUnitBean bean = new ModuleUnitBean(o.getString("path"),
                            o.getString("templet"),
                            o.getString("title"),
                            o.getInt("layoutLevel"),
                            o.getInt("extraLevel"));
                    list.add(bean);
                }
                templetList.put(key,list);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    /**
     * 排列Module列表
     * @param group
     */
    private static void sortTemplate(List<ModuleMeta> group){
        ModuleMeta exitMeta;
        for (ModuleMeta meta:group){
            Log.i(TAG,"meta ="+meta.toString());
            List<ModuleMeta> metaList = new ArrayList<>();
            if (sortgroup.get(meta.templet) != null) {
                metaList = sortgroup.get(meta.templet);
            }

            int index = 0;
            for (int i = 0; i < metaList.size(); i++) {
                exitMeta = metaList.get(i);
                if (meta.layoutlevel.getValue() < exitMeta.layoutlevel.getValue()) {  //比较层级参数,值越低,越先被加载
                    index=i;
                } else if (meta.layoutlevel.getValue() == exitMeta.layoutlevel.getValue()) {   //层级相同
                    if (meta.extralevel >= exitMeta.extralevel) {          //比较额外层级参数
                       index=i;
                    }
                }
            }

            if (index == 0){  //如果遍历后不符合条件，会添加到尾部
                metaList.add(meta);
            }else {
                metaList.add(index,meta);
            }

            sortgroup.put(meta.templet, metaList);
        }
        Log.i(TAG,"sortgroup ="+sortgroup.toString());
    }

    private static String[] split(String groupName){
        return groupName.split(",");
    }

    public static List<String> getModuleList(String templet){
//        if (moduleGroup.isEmpty()) return null;
//        List<String> list = new ArrayList<>();
//        for (ModuleUnitBean bean: moduleGroup){
//            if (bean.templet.equals(templet)){
//                list.add(bean.path);
//            }
//        }
//        return list;
        if (templetList.isEmpty()) return null;
        List<String> moduleList = new ArrayList<>();
        for (ModuleUnitBean bean: templetList.get(templet)) {
            if (bean.templet.equals(templet)){
                moduleList.add(bean.path);
            }
        }
        return moduleList;
    }

    public static List<ModuleMeta> getMouleList(String templet){
        if (sortgroup.containsKey(templet)){
            return sortgroup.get(templet);
        }
        return null;
    }

    public static List<String> getTitleList(String templet){
        if (sortgroup.containsKey(templet)) {
            List<String> namelist = new ArrayList<>();
            for (ModuleMeta meta : sortgroup.get(templet)) {
                namelist.add(meta.title);
            }
            return namelist;
        }
        return null;
    }
}
