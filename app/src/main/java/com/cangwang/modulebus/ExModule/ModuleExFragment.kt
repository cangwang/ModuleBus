package com.cangwang.modulebus.ExModule

import android.os.Bundle
import com.cangwang.core.cwmodule.ex.ModuleManageExFragment

/**
 * Created by cangwang on 2017/6/15.
 */
class ModuleExFragment : ModuleManageExFragment() {
    override fun moduleConfig(): String {
        return "normal"
    }

    companion object {
        fun newInstance(): ModuleExFragment {
            val fragment = ModuleExFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance(modules: List<String?>?): ModuleExFragment {
            PageExConfig.moduleList = modules
            val fragment = ModuleExFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}