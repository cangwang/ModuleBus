package com.cangwang.model

import com.cangwang.annotation.ModuleUnit
import com.cangwang.enums.LayoutLevel

import javax.lang.model.element.Element

/**
 * 模块信息
 * Created by cangwang on 2017/9/1.
 */

class ModuleMeta {
    //    public Element rwaType;
    var templet: String
    var moduleName: String
    var title: String
    var layoutlevel: LayoutLevel
    var extralevel: Int = 0

    constructor(templet: String, moduleName: String, title: String, layoutlevel: Int, extralevel: Int) {
        //        this.rwaType = rawType;
        this.templet = templet
        this.moduleName = moduleName
        this.title = title
        if (layoutlevel == 500) {
            this.layoutlevel = LayoutLevel.VERY_LOW
        } else if (layoutlevel == 400) {
            this.layoutlevel = LayoutLevel.LOW
        } else if (layoutlevel == 300) {
            this.layoutlevel = LayoutLevel.NORMAL
        } else if (layoutlevel == 200) {
            this.layoutlevel = LayoutLevel.HIGHT
        } else if (layoutlevel == 100) {
            this.layoutlevel = LayoutLevel.VERY_HIGHT
        }
        this.extralevel = extralevel
    }

    constructor(unit: ModuleUnit, moduleName: String) {
        //        this.rwaType = rawType;
        this.moduleName = moduleName
        this.templet = unit.templet()
        this.layoutlevel = unit.layoutlevel()
        this.extralevel = unit.extralevel()
        this.title = unit.title()
    }

    //    public static ModuleMeta build(ModuleUnit unit, Element rawType){
    //        return  new ModuleMeta(unit,rawType);
    //    }


    override fun toString(): String {
        return "ModuleMeta{" +
                "templet='" + templet + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", title='" + title + '\'' +
                ", layoutlevel=" + layoutlevel +
                ", extralevel=" + extralevel +
                '}'
    }
}
