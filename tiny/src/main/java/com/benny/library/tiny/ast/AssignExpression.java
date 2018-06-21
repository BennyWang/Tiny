package com.benny.library.tiny.ast;

import com.benny.library.tiny.RuntimeContext;
import com.benny.library.tiny.util.ValueConvertUtil;

import java.io.Serializable;

public class AssignExpression implements Expression, Serializable {
    private String name;
    private Object value;

    public AssignExpression(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Object eval(RuntimeContext context) {
        Object result = ValueConvertUtil.getValue(context, value);
        context.var(name, result);
        return result;
    }
}
