package com.benny.library.tiny.ast;

import com.benny.library.tiny.RuntimeContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FunctionExpression implements Expression, Serializable {
    private String function;
    private List<Object> args = new ArrayList<>();

    public FunctionExpression(List<Object> results) {
        function = (String) results.get(0);
        for (int i = 1; i < results.size(); ++i) {
            args.add(results.get(i));
        }
    }

    @Override
    public Object eval(RuntimeContext context) {
        List<Object> realArgs = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof Expression) {
                realArgs.add(((Expression) arg).eval(context));
            }
            else {
                realArgs.add(arg);
            }
        }
        return context.invoke(function, realArgs);
    }
}
