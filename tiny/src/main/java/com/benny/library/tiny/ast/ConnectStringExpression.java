package com.benny.library.tiny.ast;

import com.benny.library.tiny.RuntimeContext;

import java.io.Serializable;
import java.util.List;

public class ConnectStringExpression implements Expression, Serializable {
    private List<Object> results;

    public ConnectStringExpression(List<Object> results) {
        this.results = results;
    }

    @Override
    public Object eval(RuntimeContext context) {
        StringBuilder builder = new StringBuilder();
        for (Object result : results) {
            if (result instanceof Expression) {
                result = ((Expression) result).eval(context);
            }

            builder.append(result);
        }

        return builder.toString();
    }
}
