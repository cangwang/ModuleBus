package com.cangwang.bean;

/**
 * Created by cangwang on 2017/12/8.
 */

public class ModuleUnitBean {
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
}
