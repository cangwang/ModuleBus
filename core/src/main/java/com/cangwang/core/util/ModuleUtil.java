package com.cangwang.core.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by air on 2016/12/26.
 */

public class ModuleUtil {

    public static final String FACADE_PACKAGE = "com.cangwang.core";
    public static final String MODULE_UNIT = "ModuleUnit";
    public static final String SEPARATOR = "$$";
    public static final String NAME_OF_MODULEUNIT = MODULE_UNIT + SEPARATOR;
    public static final String ADDRESS_OF_MODULEUNIT = FACADE_PACKAGE+"."+MODULE_UNIT + SEPARATOR;

    public static boolean empty(Map<?,?> c){
        return c == null || c.isEmpty();
    }

    public static boolean empty(String s){
        return s == null || s.isEmpty();
    }


    public static JSONArray getAssetJsonArray(Context context,String jsonName){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager =  context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(jsonName)));
            String line;
            while ((line = bf.readLine())!=null){
                stringBuilder.append(line);
            }
            return new JSONArray(stringBuilder.toString());
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getAssetJsonObject(Context context, String jsonName){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager =  context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(jsonName)));
            String line;
            while ((line = bf.readLine())!=null){
                stringBuilder.append(line);
            }
            return new JSONObject(stringBuilder.toString());
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }


}
