package com.cangwang.core.cwmodule.api;

import java.util.Stack;

/**
 * Created by cangwang on 2018/2/24.
 */

public class BackPressStack {
    private volatile Stack<ModuleBackpress> stack;
    private static BackPressStack backPressStack;

    public BackPressStack(){
        stack  = new Stack<>();
    }

    public static BackPressStack getInstance(){
        if (backPressStack ==null){
            synchronized (BackPressStack.class){
                if (backPressStack ==null){
                    backPressStack = new BackPressStack();
                }
            }
        }
        return backPressStack;
    }

    public Stack<ModuleBackpress> getStack() {
        return stack;
    }
}
