package com.cangwang.core;

import java.util.ArrayList;

/**
 * Created by zjl on 2016/11/23.
 */

public class ModuleClient {
    Class<?> clientClass;
    ArrayList<Object> clientList;

    public ModuleClient(Class<?> clientClass,ArrayList<Object> clients){
        this.clientClass = clientClass;
        this.clientList = clients;
    }
    public ModuleClient(Class<?> clientClass){
        this.clientClass = clientClass;
    }

    public void addClient(Object c){
        if (clientList == null)
            clientList = new ArrayList<>();
        clientList.add(c);
    }
}
