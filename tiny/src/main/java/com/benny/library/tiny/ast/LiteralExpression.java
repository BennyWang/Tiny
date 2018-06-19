package com.benny.library.tiny.ast;

import com.benny.library.tiny.RuntimeContext;

public class LiteralExpression implements Expression {
    private Object result;

    public LiteralExpression(Object result) {
        this.result = result;
    }

    @Override
    public Object eval(RuntimeContext context) {
        return result;
    }
}
