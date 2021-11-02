package com.cangwang.bean

/**
 * Created by cangwang on 2017/12/8.
 */
class ModuleUnitBean(// 路径
        var path: String, // 模板
        var templet: String, // 模块
        var title: String, // 布局层级
        var layoutLevel: Int, // 布局优先
        var inflateLevel: Int, // 额外等级
        var extraLevel: Int) : Comparable<Any?> {
    override fun compareTo(o: Any?): Int {
        val b = o as ModuleUnitBean?
        return if (inflateLevel > b!!.layoutLevel) {
            1
        } else if (inflateLevel < b.layoutLevel) {
            -1
        } else {
            if (layoutLevel < b.layoutLevel) {
                1
            } else if (layoutLevel == b.layoutLevel) {
                if (extraLevel <= b.extraLevel) {
                    1
                } else -1
            } else -1
        }
    }

    override fun toString(): String {
        return "ModuleUnitBean{" +
                "path='" + path + '\'' +
                ", templet='" + templet + '\'' +
                ", title='" + title + '\'' +
                ", layoutLevel=" + layoutLevel +
                ", inflateLevel=" + inflateLevel +
                ", extraLevel=" + extraLevel +
                '}'
    }
}