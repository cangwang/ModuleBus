package com.cangwang.template

import com.cangwang.core.cwmodule.ex.ModuleManageExFragment

/**
 * Created by cangwang on 2018/2/12.
 */
class BusinessFragment : ModuleManageExFragment() {
    override fun moduleConfig(): String? {
        return "top"
    }
}