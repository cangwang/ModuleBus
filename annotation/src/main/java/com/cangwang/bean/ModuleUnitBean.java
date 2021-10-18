package com.cangwang.bean;

/**
 * Created by cangwang on 2017/12/8.
 */
public class ModuleUnitBean implements Comparable{
    public String path;      // 路径
    public String templet;  // 模板
    public String title;   // 模块
    public int layoutLevel;  // 布局层级
    public int inflateLevel; // 布局优先
    public int extraLevel;  // 额外等级

    public ModuleUnitBean(String path, String templet, String title, int layoutLevel, int inflateLevel, int extraLevel) {
        this.path = path;
        this.templet = templet;
        this.title = title;
        this.layoutLevel = layoutLevel;
        this.inflateLevel = inflateLevel;
        this.extraLevel = extraLevel;
    }

    @Override
    public int compareTo(Object o) {
        ModuleUnitBean b = (ModuleUnitBean) o;
        if (inflateLevel > b.layoutLevel) {
            return 1;
        } else if (inflateLevel < b.layoutLevel) {
            return -1;
        } else {
            if (layoutLevel < b.layoutLevel) {
                return 1;
            } else if (layoutLevel == b.layoutLevel) {
                if (extraLevel <= b.extraLevel) {
                    return 1;
                } else
                    return -1;
            } else
                return -1;
        }
    }

    @Override
    public String toString() {
        return "ModuleUnitBean{" +
                "path='" + path + '\'' +
                ", templet='" + templet + '\'' +
                ", title='" + title + '\'' +
                ", layoutLevel=" + layoutLevel +
                ", inflateLevel=" + inflateLevel +
                ", extraLevel=" + extraLevel +
                '}';
    }
}
