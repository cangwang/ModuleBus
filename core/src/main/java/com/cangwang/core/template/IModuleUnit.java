package com.cangwang.core.template;

import com.cangwang.model.ModuleMeta;

import java.util.List;

/**
 * Created by cangwang on 2017/9/4.
 */

public interface IModuleUnit {
    void loadInto(List<ModuleMeta> metaSet);
}
