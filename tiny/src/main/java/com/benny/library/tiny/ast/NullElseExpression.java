package com.benny.library.tiny.ast;

import com.benny.library.tiny.RuntimeContext;
import com.benny.library.tiny.util.ValueConvertUtil;

public class NullElseExpression implements Expression {
    private Object left;
    private Object right;

    public NullElseExpression(Object left, Object right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Object eval(RuntimeContext context) {
        Object value = ValueConvertUtil.getValue(context, left);
        if (value != null) {
            return value;
        }

        return ValueConvertUtil.getValue(context, right);
    }
}
