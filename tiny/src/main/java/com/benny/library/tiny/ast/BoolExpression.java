package com.benny.library.tiny.ast;

import com.benny.library.tiny.RuntimeContext;
import com.benny.library.tiny.util.ValueConvertUtil;

import java.io.Serializable;

public class BoolExpression implements Expression, Serializable {
    private String operator;
    private Object left;
    private Object right;

    public BoolExpression(String operator, Object left, Object right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public Object eval(RuntimeContext context) {
        Object leftValue = ValueConvertUtil.getValue(context, left);
        Object rightValue = ValueConvertUtil.getValue(context, right);

        if (leftValue == null || rightValue == null) {
            return Boolean.FALSE;
        }

        if (leftValue instanceof Number && rightValue instanceof Number) {
            return compareNumber((Number) leftValue, (Number) rightValue);
        }

        return compareString(leftValue.toString(), rightValue.toString());
    }

    private Boolean compareNumber(Number num1, Number num2) {
        switch (operator) {
            case ">":
                return num1.doubleValue() > num2.doubleValue();
            case ">=":
                return num1.doubleValue() >= num2.doubleValue();
            case "<":
                return num1.doubleValue() < num2.doubleValue();
            case "<=":
                return num1.doubleValue() <= num2.doubleValue();
            case "==":
                return num1.doubleValue() == num2.doubleValue();
            case "<>":
                return num1.doubleValue() != num2.doubleValue();
        }
        return Boolean.FALSE;
    }

    private Boolean compareString(String val1, String val2) {
        int result = val1.compareTo(val2);
        switch (operator) {
            case ">":
                return result > 0 ? Boolean.TRUE : Boolean.FALSE;
            case ">=":
                return result >= 0 ? Boolean.TRUE : Boolean.FALSE;
            case "<":
                return result < 0 ? Boolean.TRUE : Boolean.FALSE;
            case "<=":
                return result <= 0 ? Boolean.TRUE : Boolean.FALSE;
            case "==":
                return result == 0 ? Boolean.TRUE : Boolean.FALSE;
            case "<>":
                return result != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        return Boolean.FALSE;
    }
}
