package com.cangwang.core

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.util.ArrayMap
import android.util.Log

import com.cangwang.core.info.MethodInfo

import org.json.JSONObject

import java.lang.reflect.Method
import java.util.ArrayList
import java.util.Arrays
import kotlin.reflect.KClass

/**
 * Created by zjl on 16/10/19.
 */
class ModuleBus {

    var packetName = ""
        private set

    private val moduleAct = ArrayMap<String, Any>()


    fun register(client: KClass<Any>) {
        if (client == null) return

        val orginalClass = client

        val methods = orginalClass!!.getMethods()

        for (method in methods) {
            val event = method.getAnnotation(ModuleEvent::class)
            if (event != null) {
                val clientClass = event!!.coreClientClass()

                //                addClient(clientClass,client);
                //                addEventMethod(client,clientClass,method);
                addClient(clientClass, client, method)
                //                addEventMethod(clientClass, method);
                addEventMethod(client, method, event!!.single())
            }
        }
    }

    //    private void addClient(Class<?> clientClass,Object client){
    //        ArrayList<Object> clientList = moduleClients.get(clientClass);
    //
    //        if (clientList == null)
    //            clientList = new ArrayList<>();
    //
    //        clientList.add(client);
    //        moduleClients.put(clientClass,clientList);
    //    }

    private fun addClient(clientClass: KClass<*>, client: Any, m: Method) {
        var clientMethodList: ArrayMap<String, ArrayList<Any>>? = moduleMethodClient[clientClass]

        if (clientMethodList == null) {
            clientMethodList = ArrayMap()
        }

        var clientList: ArrayList<Any>? = clientMethodList[m.name]
        if (clientList == null) {
            clientList = ArrayList()
        }

        clientList.add(client)
        clientMethodList.put(m.name, clientList)
        moduleMethodClient.put(clientClass, clientMethodList)
    }

    //    private void addEventMethod(Object client,Class<?> clientClass, Method m){
    //        ArrayMap<String,Method> methods = moduleEventMethods.get(clientClass);
    //
    //        if(methods == null){
    //            methods = new ArrayMap<>();
    //            moduleEventMethods.put(clientClass,methods);
    //        }
    //        methods.put(m.getName(),m);
    //    }

    //    private void addEventMethod(Class<?> clientClass, Method m){
    //        ArrayMap<String,Method> methods = moduleEventMethods.get(clientClass);
    //
    //        if(methods == null){
    //            methods = new ArrayMap<>();
    //            moduleEventMethods.put(clientClass,methods);
    //        }
    //        methods.put(m.getName(),m);
    //    }

    private fun addEventMethod(client: Any, m: Method, single: Boolean) {
        var methods: ArrayMap<String, MethodInfo>? = moduleEventMethods[client]
        if (methods == null) {
            methods = ArrayMap()
            moduleEventMethods.put(client, methods)
        }
        methods.put(m.name, MethodInfo(m.name, m, single))
    }

    fun unregister(client: Class<*>) {
        if (client == null) return

        val orginalClass = client


        val methods = orginalClass!!.getMethods()
        for (method in methods) {
            val event = method.getAnnotation(ModuleEvent::class)
            if (event != null) {
                val clientClass = event!!.coreClientClass()
                if (moduleEventMethods[clientClass] == null) return
                moduleEventMethods[clientClass].remove(method)  //移除方法
                //                moduleClients.remove(clientClass);
                if (moduleEventMethods[clientClass].isEmpty())
                //如果此类中已经无方法，移除此类
                    moduleMethodClient.remove(clientClass)
            }
        }
    }

    //    public ArrayList<Object> getClient(Class<?> clientClass){
    //
    //        if(clientClass == null) return null;
    //        ArrayList<Object> clientList = moduleClients.get(clientClass);
    //        if(clientList != null){
    //            clientList = new ArrayList<>(clientList);
    //        }
    //
    //        return clientList;
    //    }

    fun getClient(clientClass: KClass<*>?, methodName: String?): ArrayList<Any>? {
        if (clientClass == null || methodName == null) return null
        return if (moduleMethodClient[clientClass] != null)
            moduleMethodClient[clientClass]!!.get(methodName)
        else
            null
    }

    fun post(clientClass: KClass<*>?, methodName: String?, vararg args: Bun) {
        if (clientClass == null || methodName == null || methodName.length == 0) return

        val clientList = getClient(clientClass, methodName) ?: return

        try {
            for (c in clientList) {
                try {
                    val methods = moduleEventMethods[c]
                    val method = methods!!.get(methodName)!!.m
                    if (method == null) {
                        Log.e(TAG, "cannot find client method" + methodName + "for args[" + args.size + "]" + Arrays.toString(args))
                        return
                    } else if (method.parameterTypes == null) {
                        Log.e(TAG, "cannot find client method param:" + method.parameterTypes + "for args[" + args.size + "]" + Arrays.toString(args))
                        return
                    } else if (method.parameterTypes.size != args.size) {
                        Log.e(TAG, "method " + methodName + " param number not matched:method(" + method.parameterTypes.size + "), args(" + args.size + ")")
                        return
                    }
                    method.invoke(c, *args)
                } catch (e: Throwable) {
                    Log.e(TAG, "Notifiy client method invoke error.", e)
                }

            }

        } catch (e: Throwable) {
            Log.e(TAG, "Notify client error", e)
        }

    }

    fun postSingle(clientClass: KClass<*>?, methodName: String?, vararg args: Any): Any? {
        if (clientClass == null || methodName == null || methodName.length == 0) return null

        val clientList = getClient(clientClass, methodName) ?: return null

        try {
            val methodInfo = moduleEventMethods[clientClass]!!.get(methodName)
            val single = methodInfo!!.single
            val method = methodInfo!!.m

            if (!single || clientList.size != 1) {
                Log.e(TAG, "method" + methodName + "for args[" + args.size + "]" + Arrays.toString(args) + " i s not single")
                return null
            } else if (method == null) {
                Log.e(TAG, "cannot find client method" + methodName + "for args[" + args.size + "]" + Arrays.toString(args))
                return null
            } else if (method.parameterTypes == null) {
                Log.e(TAG, "cannot find client method param:" + method.parameterTypes + "for args[" + args.size + "]" + Arrays.toString(args))
                return null
            } else if (method.parameterTypes.size != args.size) {
                Log.e(TAG, "method " + methodName + " param number not matched:method(" + method.parameterTypes.size + "), args(" + args.size + ")")
                return null
            }

            for (c in clientList) {
                try {
                    return method.invoke(c, *args)
                } catch (e: Throwable) {
                    Log.e(TAG, "Notifiy client method invoke error.", e)
                }

            }

        } catch (e: Throwable) {
            Log.e(TAG, "Notify client error", e)
            return null
        }

        return null
    }

    fun startModuleActivity(`object`: Any, className: String?, bundle: Bundle? = null) {
        if (className == null) return
        moduleAct.put(className, `object`)
        post(IBaseClient::class, "startModuleActivity", className, bundle)
    }


    fun moduleResult(`object`: Any, data: Intent?) {
        if (data == null) return
        post(IBaseClient::class, "moduleResult", moduleAct[`object`.javaClass.getName()], data)
    }

    fun setPackageName(name: String) {
        this.packetName = name
    }

    fun getModuleList(templet: String): List<String>? {
        return ModuleCenter.getModuleList(templet)
    }

    companion object {
        private val TAG = "ModuleBus"

        val MODULE_RESULT = 1001

        //    private static ArrayMap<Object,ArrayMap<String,Method>> moduleEventMethods = new ArrayMap<>();
        /**
         * Object methodClass
         * String methodName；
         * MethodInfo method info
         */
        private val moduleEventMethods = ArrayMap<Any, ArrayMap<String, MethodInfo>>()
        //    private static ArrayMap<Class<?>,ArrayList<Object>> moduleClients = new ArrayMap<>();

        /**
         * Class IBaseClient.class
         * String methodName
         * Object methodClass
         */
        private val moduleMethodClient = ArrayMap<KClass<*>, ArrayMap<String, ArrayList<Any>>>()

        var instance: ModuleBus = ModuleBus()

        fun init(context: Context) {
            ModuleCenter.init(context)
        }

        fun init(context: Context, `object`: JSONObject) {
            ModuleCenter.init(context, `object`, true)
        }
    }

}
