package com.cangwang.model;

import com.cangwang.annotation.ModuleUnit;
import com.cangwang.enums.LayoutLevel;

import javax.lang.model.element.Element;

/**
 * 模块信息
 * Created by cangwang on 2017/9/1.
 */

public class ModuleMeta {
//    public Element rwaType;
    public String moduleName;
    public String templet;
    public LayoutLevel layoutlevel;
    public int extralevel;

    public ModuleMeta(String templet,String moduleName,int layoutlevel,int extralevel){
//        this.rwaType = rawType;
        this.moduleName = moduleName;
        this.templet = templet;
        this.layoutlevel = LayoutLevel.values()[layoutlevel];
        this.extralevel = extralevel;
    }

    public ModuleMeta(ModuleUnit unit, Element rawType){
//        this.rwaType = rawType;
        this.moduleName = rawType.getSimpleName().toString();
        this.templet = unit.templet();
        this.layoutlevel = unit.layoutlevel();
        this.extralevel = unit.extralevel();
    }

    public static ModuleMeta build(ModuleUnit unit, Element rawType){
        return  new ModuleMeta(unit,rawType);
    }
}
