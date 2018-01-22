package com.cangwang.bean

/**
 * Created by cangwang on 2017/12/8.
 */

class ModuleUnitBean(var path: String, var templet: String, var title: String, var layoutLevel: Int, var extraLevel: Int) : Comparable<ModuleUnitBean> {

    override operator fun compareTo(o: ModuleUnitBean): Int {
        return if (layoutLevel < o.layoutLevel) {
            1
        } else if (layoutLevel == o.layoutLevel) {
            if (extraLevel <= o.extraLevel) {
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
