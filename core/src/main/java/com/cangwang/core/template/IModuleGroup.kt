package com.cangwang.core.template

import com.cangwang.model.ModuleMeta

/**
 * Created by cangwang on 2017/8/31.
 */
interface IModuleGroup {
    fun loadInto(info: Map<String?, ModuleMeta?>?)
}