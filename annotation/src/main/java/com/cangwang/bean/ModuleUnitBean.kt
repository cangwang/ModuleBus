package com.cangwang.bean

/**
 * Created by cangwang on 2017/12/8.
 */

class ModuleUnitBean(var path: String, var templet: String, var title: String, var layoutLevel: Int, var extraLevel: Int) : Comparable<*> {

    override operator fun compareTo(o: Any): Int {
        val b = o as ModuleUnitBean
        return if (layoutLevel < b.layoutLevel) {
            1
        } else if (layoutLevel == b.layoutLevel) {
            if (extraLevel <= b.extraLevel) {
                1
            } else
                -1
        } else
            -1
    }

    override fun toString(): String {
        return "ModuleUnitBean{" +
                "path='" + path + '\'' +
                ", templet='" + templet + '\'' +
                ", title='" + title + '\'' +
                ", layoutLevel=" + layoutLevel +
                ", extraLevel=" + extraLevel +
                '}'
    }
}
