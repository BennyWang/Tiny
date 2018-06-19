package com.benny.library.tiny.ast;

import com.benny.library.tiny.RuntimeContext;

public class VarExpression implements Expression {
    private String name;

    public VarExpression(String name) {
        this.name = name;
    }

    @Override
    public Object eval(RuntimeContext context) {
        return name.startsWith("$") ? context.var(name) : context.global(name);
    }
}
