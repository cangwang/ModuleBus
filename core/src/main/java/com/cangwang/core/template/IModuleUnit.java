package com.cangwang.core.template;

import com.cangwang.model.ModuleMeta;
import java.util.Set;

/**
 * Created by cangwang on 2017/9/4.
 */

public interface IModuleUnit {
    void loadInto(Set<ModuleMeta> metaSet);
}
