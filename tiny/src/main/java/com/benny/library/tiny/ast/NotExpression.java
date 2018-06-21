package com.benny.library.tiny.ast;

import com.benny.library.tiny.RuntimeContext;
import com.benny.library.tiny.util.ValueConvertUtil;

import java.io.Serializable;

public class NotExpression implements Expression, Serializable {
    private Object value;
    public NotExpression(Object value) {
        this.value = value;
    }

    @Override
    public Object eval(RuntimeContext context) {
        Object result = value;
        if (result instanceof Expression) {
            result = ((Expression) value).eval(context);
        }
        return !ValueConvertUtil.convertBoolean(result);
    }
}
