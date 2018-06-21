package com.benny.library.tiny;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuntimeContext {
    private Map<String, Object> variables = new HashMap<>();
    private Map<String, Object> globals = new HashMap<>();

    public RuntimeContext() {
    }

    public RuntimeContext(Map<String, Object> variables, Map<String, Object> globals) {
        this.variables.putAll(variables);
        this.globals.putAll(globals);
    }

    public Object var(String name) {
        return variables.get(name);
    }

    public void var(String name, Object value) {
        variables.put(name, value);
    }

    public Object global(String name) {
        return globals.get(name);
    }

    public void global(String name, Object value) {
        globals.put(name, value);
    }

    public Object invoke(String function, List<Object> args) {
        System.out.println("call " + function + " with args: " + args);
        return null;
    }
}
