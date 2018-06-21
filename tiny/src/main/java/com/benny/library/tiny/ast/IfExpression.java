package com.benny.library.tiny.ast;

import com.benny.library.tiny.RuntimeContext;
import com.benny.library.tiny.util.ValueConvertUtil;

import java.io.Serializable;

public class IfExpression implements Expression, Serializable {
    private Object condition;
    private Object trueStmt;
    private Object falseStmt;

    public IfExpression(Object condition, Object trueStmt, Object falseStmt) {
        this.condition = condition;
        this.trueStmt = trueStmt;
        this.falseStmt = falseStmt;
    }

    @Override
    public Object eval(RuntimeContext context) {
        Boolean result = ValueConvertUtil.convertBoolean(context, condition);
        if (result) {
            if (trueStmt != null) {
                return ValueConvertUtil.getValue(context, trueStmt);
            }
        }
        else {
            if (falseStmt != null) {
                return ValueConvertUtil.getValue(context, falseStmt);
            }
        }
        return null;
    }
}
