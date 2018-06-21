package com.benny.library.tiny.util;

import com.benny.library.tiny.RuntimeContext;
import com.benny.library.tiny.ast.Expression;

public class ValueConvertUtil {

    public static Object getValue(RuntimeContext context, Object arg) {
        if (arg instanceof Expression) {
            return ((Expression) arg).eval(context);
        }
        return arg;
    }

    public static Number convertNumber(RuntimeContext context, Object arg) {
        if (arg instanceof Expression) {
            return (Number) ((Expression) arg).eval(context);
        }
        else if (arg instanceof Number) {
            return (Number) arg;
        }

        throw new RuntimeException("illegal number argument: " + arg);
    }

    public static Boolean convertBoolean(RuntimeContext context, Object result) {
        if (result instanceof Expression) {
            return convertBoolean(((Expression) result).eval(context));
        }
        return convertBoolean(result);
    }

    public static Boolean convertBoolean(Object result) {
        if (result == null) {
            return Boolean.FALSE;
        }
        else if (result instanceof Boolean) {
            return (Boolean)result;
        }
        else if (result instanceof Number) {
            return ((Number) result).longValue() != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        else if (result instanceof String) {
            String str = (String) result;
            if (str.isEmpty() || str.equalsIgnoreCase("false")) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
}
