package com.cangwang.core.template

import com.cangwang.model.ModuleMeta

/**
 * Created by cangwang on 2017/9/4.
 */

interface IModuleUnit {
    fun loadInto(metaSet: List<ModuleMeta>)
}
