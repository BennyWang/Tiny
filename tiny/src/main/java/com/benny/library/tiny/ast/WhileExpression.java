package com.benny.library.tiny.ast;

import com.benny.library.tiny.RuntimeContext;
import com.benny.library.tiny.util.ValueConvertUtil;

import java.io.Serializable;

public class WhileExpression implements Expression, Serializable {
    private Object condition;
    private Object statements;

    public WhileExpression(Object condition, Object statements) {
        this.condition = condition;
        this.statements = statements;
    }

    @Override
    public Object eval(RuntimeContext context) {
        while (ValueConvertUtil.convertBoolean(context, condition) && statements != null) {
            ((Expression) statements).eval(context);
        }
        return null;
    }
}
