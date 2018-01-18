package com.cangwang.core.cwmodule.ex

import com.cangwang.model.ICWModule
import com.cangwang.model.IModuleFactory

import java.util.ArrayList
import java.util.HashMap
import java.util.LinkedList

/**
 * Created by yyinc on 2017/12/17.
 */

class CWFacotry : IModuleFactory {

    override fun getTempleList(templet: String): List<ICWModule> {
        return moduleMap[templet]
    }

    companion object {
        var moduleMap: Map<String, List<ICWModule>> = HashMap()

        init {
            val list = LinkedList<ICWModule>()
            //        list.add();
            //        moduleMap.put("normal",);
            list.clear()
        }
    }
}
