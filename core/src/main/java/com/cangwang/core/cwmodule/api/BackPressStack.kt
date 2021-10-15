package com.cangwang.core.cwmodule.api

import java.util.*

/**
 * Created by cangwang on 2018/2/24.
 */
object BackPressStack {
    @Volatile
    var stack: Stack<ModuleBackpress> = Stack()
}