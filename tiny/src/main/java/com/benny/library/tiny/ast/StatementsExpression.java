package com.benny.library.tiny.ast;

import com.benny.library.tiny.RuntimeContext;

import java.io.Serializable;
import java.util.List;

public class StatementsExpression implements Expression, Serializable {
    private List<Object> statements;

    public StatementsExpression(List<Object> statements) {
        this.statements = statements;
    }

    @Override
    public Object eval(RuntimeContext context) {
        for (Object statement : statements) {
            ((Expression) statement).eval(context);
        }
        return null;
    }
}
