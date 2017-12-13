package com.cangwang.bean;

/**
 * Created by cangwang on 2017/12/8.
 */

public class ModuleUnitBean implements Comparable{
    public String path;
    public String templet;
    public String title;
    public int layoutLevel;
    public int extraLevel;

    public ModuleUnitBean(String path, String templet, String title, int layoutLevel, int extraLevel) {
        this.path = path;
        this.templet = templet;
        this.title = title;
        this.layoutLevel = layoutLevel;
        this.extraLevel = extraLevel;
    }

    @Override
    public int compareTo(Object o) {
        ModuleUnitBean b =(ModuleUnitBean)o;
        if (layoutLevel < b.layoutLevel){
            return 1;
        }else if (layoutLevel == b.layoutLevel){
            if (extraLevel <=b.extraLevel){
                return 1;
            }else return -1;
        }else return -1;
    }

    @Override
    public String toString() {
        return "ModuleUnitBean{" +
                "path='" + path + '\'' +
                ", templet='" + templet + '\'' +
                ", title='" + title + '\'' +
                ", layoutLevel=" + layoutLevel +
                ", extraLevel=" + extraLevel +
                '}';
    }
}
