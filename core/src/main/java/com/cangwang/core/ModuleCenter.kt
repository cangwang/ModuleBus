package com.cangwang.core

import org.json.JSONException
import android.content.Context
import com.cangwang.model.ModuleMeta
import android.util.Log
import com.cangwang.bean.ModuleUnitBean
import com.cangwang.core.util.ModuleUtil
import org.json.JSONObject
import java.util.ArrayList
import java.util.HashMap

/**
 * Module loading
 * Created by cangwang on 2017/9/4.
 */
object ModuleCenter {
    private const val TAG = "ModuleCenter"
    private val group: List<ModuleMeta> = ArrayList()
    private val sortgroup: MutableMap<String, MutableList<ModuleMeta>?> = HashMap()
    private val moduleGroup: List<ModuleUnitBean> = ArrayList()
    private val templetList: MutableMap<String, List<ModuleUnitBean>> = HashMap()
    var isFromNetWork = false
    @Synchronized
    fun init(context: Context) {
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
        init(context, ModuleUtil.getAssetJsonObject(context, "center.json"), false)
    }

    @Synchronized
    fun init(context: Context?, `object`: JSONObject?, isFromNet: Boolean) {
        isFromNetWork = isFromNet
        if (`object` == null) return
        Log.e(TAG, "templet = $`object`")
        try {
            val iterator: Iterator<*> = `object`.keys()
            while (iterator.hasNext()) {
                val key = iterator.next().toString()
                val array = `object`.getJSONArray(key)
                val list: MutableList<ModuleUnitBean> = ArrayList()
                val length = array.length()
                for (i in 0 until length) {
                    val o = array.getJSONObject(i)
                    val bean = ModuleUnitBean(o.getString("path"),
                            o.getString("templet"),
                            o.getString("title"),
                            o.getInt("layoutLevel"),
                            o.getInt("extraLevel"))
                    list.add(bean)
                }
                templetList[key] = list
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    /**
     * 排列Module列表
     * @param group
     */
    private fun sortTemplate(group: List<ModuleMeta>) {
        var exitMeta: ModuleMeta
        for (meta in group) {
            Log.i(TAG, "meta =$meta")
            var metaList: MutableList<ModuleMeta>? = ArrayList()
            if (sortgroup[meta.templet] != null) {
                metaList = sortgroup[meta.templet]
            }
            var index = 0
            for (i in metaList!!.indices) {
                exitMeta = metaList[i]
                if (meta.layoutlevel.value < exitMeta.layoutlevel.value) {  //比较层级参数,值越低,越先被加载
                    index = i
                } else if (meta.layoutlevel.value == exitMeta.layoutlevel.value) {   //层级相同
                    if (meta.extralevel >= exitMeta.extralevel) {          //比较额外层级参数
                        index = i
                    }
                }
            }
            if (index == 0) {  //如果遍历后不符合条件，会添加到尾部
                metaList.add(meta)
            } else {
                metaList.add(index, meta)
            }
            sortgroup[meta.templet] = metaList
        }
        Log.i(TAG, "sortgroup =" + sortgroup.toString())
    }

    private fun split(groupName: String): Array<String> {
        return groupName.split(",").toTypedArray()
    }

    fun getModuleList(templet: String?): List<String>? {
//        if (moduleGroup.isEmpty()) return null;
//        List<String> list = new ArrayList<>();
//        for (ModuleUnitBean bean: moduleGroup){
//            if (bean.templet.equals(templet)){
//                list.add(bean.path);
//            }
//        }
//        return list;
        if (templetList.isEmpty()) return null
        val moduleList: MutableList<String> = ArrayList()
        for (bean in templetList[templet]!!) {
            if (bean.templet == templet) {
                moduleList.add(bean.path)
            }
        }
        return moduleList
    }

    fun getMouleList(templet: String?): List<ModuleMeta>? {
        return if (sortgroup.containsKey(templet)) {
            sortgroup[templet]
        } else null
    }

    fun getTitleList(templet: String?): List<String>? {
        if (sortgroup.containsKey(templet)) {
            val namelist: MutableList<String> = ArrayList()
            for (meta in sortgroup[templet]!!) {
                namelist.add(meta.title)
            }
            return namelist
        }
        return null
    }
}