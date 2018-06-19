package com.benny.library.tiny;

import java.util.List;

public class RuntimeContext {

    public Object var(String name) {
        return null;
    }

    public Object global(String name) {
        return null;
    }

    public Object invoke(String function, List<Object> args) {
        System.out.println("call " + function + " with args: " + args);
        return null;
    }
}
