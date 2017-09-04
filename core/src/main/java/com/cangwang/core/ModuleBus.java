package com.cangwang.core;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.cangwang.core.info.MethodInfo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by zjl on 16/10/19.
 */
public class ModuleBus {
    private static final String TAG = "ModuleBus";

    public static final int MODULE_RESULT = 1001;

//    private static ArrayMap<Object,ArrayMap<String,Method>> moduleEventMethods = new ArrayMap<>();
    /**
     * Object methodClass
     * String methodName；
     * MethodInfo method info
     */
    private static ArrayMap<Object,ArrayMap<String,MethodInfo>> moduleEventMethods = new ArrayMap<>();
//    private static ArrayMap<Class<?>,ArrayList<Object>> moduleClients = new ArrayMap<>();

    /**
     * Class IBaseClient.class
     * String methodName
     * Object methodClass
     */
    private static ArrayMap<Class<?>,ArrayMap<String,ArrayList<Object>>> moduleMethodClient = new ArrayMap<>();

    private static ModuleBus instance;

    private String PK_NAME ="";

    public static ModuleBus getInstance(){
        if(instance == null){
            synchronized (ModuleBus.class){
                if (instance == null){
                    instance = new ModuleBus();
                }
            }
        }
        return instance;
    }


    public void register(Object client){
        if(client == null) return;

        Class<?> orginalClass = client.getClass();
        if(orginalClass == null) return;


        Method[] methods = orginalClass.getMethods();

        for(Method method:methods){
            ModuleEvent event = method.getAnnotation(ModuleEvent.class);
            if(event !=null){
                Class<?> clientClass = event.coreClientClass();

//                addClient(clientClass,client);
//                addEventMethod(client,clientClass,method);
                addClient(clientClass, client, method);
//                addEventMethod(clientClass, method);
                addEventMethod(client,method,event.single());
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

    private void addClient(Class<?> clientClass,Object client,Method m){
        ArrayMap<String,ArrayList<Object>> clientMethodList = moduleMethodClient.get(clientClass);

        if (clientMethodList == null) {
            clientMethodList = new ArrayMap<>();
        }

        ArrayList<Object> clientList = clientMethodList.get(m.getName());
        if (clientList ==null){
            clientList = new ArrayList<>();
        }

        clientList.add(client);
        clientMethodList.put(m.getName(),clientList);
        moduleMethodClient.put(clientClass,clientMethodList);
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

    private void addEventMethod(Object client, Method m,boolean single){
            ArrayMap<String, MethodInfo> methods = moduleEventMethods.get(client);
            if (methods == null) {
                methods = new ArrayMap<>();
                moduleEventMethods.put(client, methods);
            }
            methods.put(m.getName(), new MethodInfo(m.getName(), m, single));
    }

    public void unregister(Object client){
        if(client == null) return;

        Class<?> orginalClass = client.getClass();
        if(orginalClass == null) return;


        Method[] methods = orginalClass.getMethods();
        for(Method method:methods) {
            ModuleEvent event = method.getAnnotation(ModuleEvent.class);
            if(event !=null) {
                Class<?> clientClass = event.coreClientClass();
                if (moduleEventMethods.get(clientClass)==null) return;
                moduleEventMethods.get(clientClass).remove(method);  //移除方法
//                moduleClients.remove(clientClass);
                if (moduleEventMethods.get(clientClass).isEmpty())   //如果此类中已经无方法，移除此类
                    moduleMethodClient.remove(clientClass);
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

    public ArrayList<Object> getClient(Class<?> clientClass,String methodName){
        if(clientClass == null || methodName == null) return null;
        if (moduleMethodClient.get(clientClass)!=null)
            return moduleMethodClient.get(clientClass).get(methodName);
        else
            return null;
    }

    public void post(Class<?> clientClass,String methodName,Object...args){
        if(clientClass == null || methodName == null ||methodName.length() == 0) return;

        ArrayList<Object> clientList = getClient(clientClass,methodName);

        if(clientList == null) return;

        try{
            for(Object c: clientList){
                try{
                    ArrayMap<String,MethodInfo> methods = moduleEventMethods.get(c);
                    Method method = methods.get(methodName).m;
                    if(method == null){
                        Log.e(TAG,"cannot find client method"+methodName +"for args["+args.length+"]" + Arrays.toString(args));
                        return;
                    }else if(method.getParameterTypes() == null){
                        Log.e(TAG,"cannot find client method param:"+method.getParameterTypes() +"for args["+args.length+"]" + Arrays.toString(args));
                        return;
                    }else if(method.getParameterTypes().length != args.length){
                        Log.e(TAG,"method "+methodName +" param number not matched:method("+method.getParameterTypes().length+"), args(" + args.length+")");
                        return;
                    }
                    method.invoke(c,args);
                }catch (Throwable e){
                    Log.e(TAG,"Notifiy client method invoke error.",e);
                }
            }

        }catch (Throwable e){
            Log.e(TAG,"Notify client error",e);
        }
    }

    public Object postSingle(Class<?> clientClass,String methodName,Object...args){
        if(clientClass == null || methodName == null ||methodName.length() == 0) return null;

        ArrayList<Object> clientList = getClient(clientClass,methodName);

        if(clientList == null) return null;

        try{
            MethodInfo methodInfo = moduleEventMethods.get(clientClass).get(methodName);
            boolean single = methodInfo.single;
            Method method = methodInfo.m;

            if (!single || clientList.size() != 1){
                Log.e(TAG,"method"+methodName +"for args["+args.length+"]" + Arrays.toString(args) + " i s not single");
                return null;
            } else if(method == null){
                Log.e(TAG,"cannot find client method"+methodName +"for args["+args.length+"]" + Arrays.toString(args));
                return null;
            }else if(method.getParameterTypes() == null){
                Log.e(TAG,"cannot find client method param:"+method.getParameterTypes() +"for args["+args.length+"]" + Arrays.toString(args));
                return null;
            }else if(method.getParameterTypes().length != args.length){
                Log.e(TAG,"method "+methodName +" param number not matched:method("+method.getParameterTypes().length+"), args(" + args.length+")");
                return null;
            }

            for(Object c: clientList){
                try{
                    return method.invoke(c,args);
                }catch (Throwable e){
                    Log.e(TAG,"Notifiy client method invoke error.",e);
                }
            }

        }catch (Throwable e){
            Log.e(TAG,"Notify client error",e);
            return null;
        }
        return null;
    }

    private ArrayMap<String,Object> moduleAct = new ArrayMap<>();

    public void startModuleActivity(Object object,String className){
        startModuleActivity(object,className,null);
    }

    public void startModuleActivity(Object object,String className, Bundle bundle){
        if(className == null) return;
        moduleAct.put(className,object);
        post(IBaseClient.class,"startModuleActivity",className,bundle);
    }


    public void moduleResult(Object object,Intent data){
        if (data==null)return;
        post(IBaseClient.class,"moduleResult",moduleAct.get(object.getClass().getName()),data);
    }

    public void setPackageName(String name){
        this.PK_NAME = name;
    }

    public String getPacketName(){
        return PK_NAME;
    }

    public static void init(Context context){
        ModuleCenter.init(context);
    }

}
