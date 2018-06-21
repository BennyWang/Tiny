package com.benny.library.tiny.ast;

import com.benny.library.tiny.RuntimeContext;
import com.benny.library.tiny.util.ValueConvertUtil;

import java.io.Serializable;

public class BoolOperatorExpression implements Expression, Serializable {
    private String operator;
    private Object left;
    private Object right;

    public BoolOperatorExpression(String operator, Object left, Object right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public Object eval(RuntimeContext context) {
        Boolean leftValue = ValueConvertUtil.convertBoolean(context, left);
        Boolean rightValue = ValueConvertUtil.convertBoolean(context, right);
        switch (operator) {
            case "and":
                return leftValue && rightValue;
            case "or":
                return leftValue || rightValue;
        }

        return Boolean.FALSE;
    }
}
