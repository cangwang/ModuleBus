package com.cangwang.core.util

import com.cangwang.core.cwmodule.ex.CWAbsExModule
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.pm.ApplicationInfo
import android.content.res.AssetManager
import org.json.JSONException
import com.cangwang.model.ICWModule
import com.cangwang.core.cwmodule.ex.CWFacotry
import com.cangwang.core.cwmodule.CWModuleContext
import android.os.Bundle
import com.cangwang.core.MBaseApi
import androidx.fragment.app.FragmentActivity
import android.view.LayoutInflater
import com.cangwang.core.cwmodule.api.ModuleBackpress
import androidx.annotation.CallSuper
import com.cangwang.core.cwmodule.api.BackPressStack
import androidx.annotation.LayoutRes
import com.cangwang.core.ModuleApiManager
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.os.Looper
import com.cangwang.core.cwmodule.ex.CWModuleExFactory
import com.cangwang.core.cwmodule.ex.ModuleExManager
import com.cangwang.core.R
import com.cangwang.core.ModuleBus
import androidx.collection.SparseArrayCompat
import com.cangwang.core.ModuleCenter
import com.cangwang.core.ModuleEvent
import com.cangwang.core.IBaseClient
import com.cangwang.core.cwmodule.ex.ModuleLoadListener
import androidx.appcompat.app.AppCompatActivity
import com.cangwang.model.ModuleMeta
import android.content.Intent
import com.cangwang.bean.ModuleUnitBean
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder

/**
 * Created by air on 2016/12/26.
 */
object ModuleUtil {
    const val FACADE_PACKAGE = "com.cangwang.core"
    const val MODULE_UNIT = "ModuleUnit"
    const val SEPARATOR = "$$"
    const val NAME_OF_MODULEUNIT = MODULE_UNIT + SEPARATOR
    const val ADDRESS_OF_MODULEUNIT = FACADE_PACKAGE + "." + MODULE_UNIT + SEPARATOR
    fun empty(c: Map<*, *>?): Boolean {
        return c == null || c.isEmpty()
    }

    fun empty(s: String?): Boolean {
        return s == null || s.isEmpty()
    }

    fun getAssetJsonArray(context: Context, jsonName: String?): JSONArray? {
        val stringBuilder = StringBuilder()
        try {
            val assetManager = context.assets
            val bf = BufferedReader(InputStreamReader(assetManager.open(jsonName!!)))
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            return JSONArray(stringBuilder.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    fun getAssetJsonObject(context: Context, jsonName: String?): JSONObject? {
        val stringBuilder = StringBuilder()
        try {
            val assetManager = context.assets
            val bf = BufferedReader(InputStreamReader(assetManager.open(jsonName!!)))
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            return JSONObject(stringBuilder.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }
}