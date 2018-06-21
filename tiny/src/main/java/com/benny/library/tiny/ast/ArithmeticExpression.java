package com.benny.library.tiny.ast;

import com.benny.library.tiny.RuntimeContext;
import com.benny.library.tiny.util.ValueConvertUtil;

import java.io.Serializable;

public class ArithmeticExpression implements Expression, Serializable {
    private String operator;
    private Object left;
    private Object right;

    public ArithmeticExpression(String operator, Object left, Object right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public Object eval(RuntimeContext context) {
        Number leftValue = ValueConvertUtil.convertNumber(context, left);
        Number rightValue = ValueConvertUtil.convertNumber(context, right);
        switch (operator) {
            case "+":
                return leftValue.doubleValue() + rightValue.doubleValue();
            case "-":
                return leftValue.doubleValue() - rightValue.doubleValue();
            case "*":
                return leftValue.doubleValue() * rightValue.doubleValue();
            case "/":
                return leftValue.doubleValue() / rightValue.doubleValue();
        }

        return null;
    }
}
