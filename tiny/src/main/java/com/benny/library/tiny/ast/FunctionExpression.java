package com.benny.library.tiny.ast;

import com.benny.library.tiny.RuntimeContext;

import java.util.ArrayList;
import java.util.List;

public class FunctionExpression implements Expression {
    private String function;
    private List<Object> args = new ArrayList<>();

    public FunctionExpression(List<Object> results) {
        function = (String) results.get(0);
        for (int i = 2; i < results.size() - 1; ++i) {
            args.add(results.get(i));
        }
    }

    @Override
    public Object eval(RuntimeContext context) {
        for (int i = 0; i < args.size(); ++i) {
            Object arg = args.get(i);
            if (arg instanceof Expression) {
                args.set(i, ((Expression) arg).eval(context));
            }
        }

        return context.invoke(function, args);
    }
}
