package com.cangwang.core

import java.util.ArrayList

/**
 * Created by zjl on 2016/11/23.
 */

class ModuleClient {
    internal var clientClass: Class<*>
    internal var clientList: ArrayList<Any>? = null

    constructor(clientClass: Class<*>, clients: ArrayList<Any>) {
        this.clientClass = clientClass
        this.clientList = clients
    }

    constructor(clientClass: Class<*>) {
        this.clientClass = clientClass
    }

    fun addClient(c: Any) {
        if (clientList == null)
            clientList = ArrayList()
        clientList!!.add(c)
    }
}
