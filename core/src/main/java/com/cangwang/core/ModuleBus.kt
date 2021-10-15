package com.cangwang.core

import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.collection.ArrayMap
import com.cangwang.core.info.MethodInfo
import org.json.JSONObject
import java.lang.reflect.Method
import java.util.*
import kotlin.reflect.KClass

/**
 * Created by zjl on 16/10/19.
 */
class ModuleBus {
    var packetName = ""
        private set

    fun register(client: Any?) {
        if (client == null) return
        val orginalClass: Class<Any> = client.javaClass ?: return
        val methods = orginalClass.methods
        for (method in methods) {
            val event = method.getAnnotation(ModuleEvent::class.java)
            if (event != null) {
                val clientClass: KClass<*> = event.coreClientClass

//                addClient(clientClass,client);
//                addEventMethod(client,clientClass,method);
                addClient(clientClass, client, method)
                //                addEventMethod(clientClass, method);
                addEventMethod(client, method, event.single)
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
        var clientMethodList = moduleMethodClient[clientClass]
        if (clientMethodList == null) {
            clientMethodList = ArrayMap()
        }
        var clientList = clientMethodList[m.name]
        if (clientList == null) {
            clientList = ArrayList()
        }
        clientList.add(client)
        clientMethodList[m.name] = clientList
        moduleMethodClient[clientClass] = clientMethodList
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
        var methods = moduleEventMethods[client]
        if (methods == null) {
            methods = ArrayMap()
            moduleEventMethods[client] = methods
        }
        methods[m.name] = MethodInfo(m.name, m, single)
    }

    fun unregister(client: Any?) {
        if (client == null) return
        val orginalClass: Class<Any> = client.javaClass ?: return
        val methods = orginalClass.methods
        for (method in methods) {
            val event = method.getAnnotation(ModuleEvent::class.java)
            if (event != null) {
                val clientClass: KClass<*> = event.coreClientClass
                if (moduleEventMethods[clientClass] == null) return
                moduleEventMethods[clientClass]!!.remove(method.toString()) //移除方法
                //                moduleClients.remove(clientClass);
                if (moduleEventMethods[clientClass]!!.isEmpty) //如果此类中已经无方法，移除此类
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
    fun getClient(clientClass: KClass<*>?, methodName: String?): ArrayList<Any?>? {
        if (clientClass == null || methodName == null) return null
        return if (moduleMethodClient[clientClass] != null) moduleMethodClient[clientClass]!![methodName] else null
    }

    fun post(clientClass: KClass<*>?, methodName: String?, vararg args: Any?) {
        if (clientClass == null || methodName == null || methodName.isEmpty()) return
        val clientList = getClient(clientClass, methodName) ?: return
        try {
            for (c in clientList) {
                val any = try {
                    val methods = moduleEventMethods[c]
                    val method = methods!![methodName]!!.m
                    if (method.parameterTypes.size != args.size) {
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

    fun postSingle(clientClass: KClass<*>?, methodName: String?, vararg args: Any?): Any? {
        if (clientClass == null || methodName == null || methodName.isEmpty()) return null
        val clientList = getClient(clientClass, methodName) ?: return null
        try {
            val methodInfo = moduleEventMethods[clientClass]!![methodName]
            val single = methodInfo!!.single
            val method = methodInfo.m
            if (!single || clientList.size != 1) {
                Log.e(TAG, "method" + methodName + "for args[" + args.size + "]" + Arrays.toString(args) + " i s not single")
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

    private val moduleAct = ArrayMap<String, Any>()
    @JvmOverloads
    fun startModuleActivity(`object`: Any, className: String?, bundle: Bundle? = null) {
        if (className == null) return
        moduleAct[className] = `object`
        post(IBaseClient::class, "startModuleActivity", className, bundle)
    }

    fun moduleResult(`object`: Any, data: Intent?) {
        if (data == null) return
        post(IBaseClient::class, "moduleResult", moduleAct[`object`.javaClass.name], data)
    }

    fun setPackageName(name: String) {
        packetName = name
    }

    fun getModuleList(template: String?): List<String?>? {
        return ModuleCenter.getModuleList(template)
    }

    companion object {
        private const val TAG = "ModuleBus"
        const val MODULE_RESULT = 1001
        //    private static ArrayMap<Object,ArrayMap<String,Method>> moduleEventMethods = new ArrayMap<>();
        /**
         * Object methodClass
         * String methodName；
         * MethodInfo method info
         */
        private val moduleEventMethods = ArrayMap<Any, ArrayMap<String?, MethodInfo?>>()
        //    private static ArrayMap<Class<?>,ArrayList<Object>> moduleClients = new ArrayMap<>();
        /**
         * Class IBaseClient.class
         * String methodName
         * Object methodClass
         */
        private val moduleMethodClient = ArrayMap<KClass<*>, ArrayMap<String?, ArrayList<Any?>?>>()
        var instance: ModuleBus? = null
            get() {
                if (field == null) {
                    synchronized(ModuleBus::class.java) {
                        if (field == null) {
                            field = ModuleBus()
                        }
                    }
                }
                return field
            }
            private set

        fun init(context: Context) {
            ModuleCenter.init(context)
        }

        fun init(context: Context?, `object`: JSONObject?) {
            ModuleCenter.init(context, `object`, true)
        }
    }
}