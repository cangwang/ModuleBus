package com.cangwang.core.util

import android.content.Context
import android.content.res.AssetManager
import android.util.Log

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Created by air on 2016/12/26.
 */

object ModuleUtil {

    val FACADE_PACKAGE = "com.cangwang.core"
    val MODULE_UNIT = "ModuleUnit"
    val SEPARATOR = "$$"
    val NAME_OF_MODULEUNIT = MODULE_UNIT + SEPARATOR
    val ADDRESS_OF_MODULEUNIT = FACADE_PACKAGE + "." + MODULE_UNIT + SEPARATOR

    fun empty(c: Map<*, *>?): Boolean {
        return c == null || c.isEmpty()
    }

    fun empty(s: String?): Boolean {
        return s == null || s.isEmpty()
    }


    fun getAssetJsonArray(context: Context, jsonName: String): JSONArray? {
        val stringBuilder = StringBuilder()
        try {
            val assetManager = context.assets
            val bf = BufferedReader(InputStreamReader(assetManager.open(jsonName)))
            var line: String
            while ((line = bf.readLine()) != null) {
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

    fun getAssetJsonObject(context: Context, jsonName: String): JSONObject? {
        val stringBuilder = StringBuilder()
        try {
            val assetManager = context.assets
            val bf = BufferedReader(InputStreamReader(assetManager.open(jsonName)))
            var line: String
            while ((line = bf.readLine()) != null) {
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
