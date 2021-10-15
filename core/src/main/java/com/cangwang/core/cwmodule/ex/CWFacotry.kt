package com.cangwang.core.cwmodule.ex

import com.cangwang.model.ICWModule
import com.cangwang.model.IModuleFactory
import java.util.*

/**
 * Created by yyinc on 2017/12/17.
 */
class CWFacotry : IModuleFactory {
    companion object {
        var moduleMap: Map<String, List<ICWModule>> = HashMap()

        init {
            val list: List<ICWModule> = LinkedList()
            //        list.add();
//        moduleMap.put("normal",);
        }
    }

    override fun getTempleList(templet: String): List<ICWModule> {
        return moduleMap[templet]!!
    }
}