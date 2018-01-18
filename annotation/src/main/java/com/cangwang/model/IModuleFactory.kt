package com.cangwang.model

/**
 * Created by cangwang on 2017/12/15.
 */

interface IModuleFactory {
    //    IModuleFactory getInstance();
    fun getTempleList(templet: String): List<ICWModule>
}
