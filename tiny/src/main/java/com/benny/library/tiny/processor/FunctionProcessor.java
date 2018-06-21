package com.benny.library.tiny.processor;

import com.benny.library.tiny.ast.FunctionExpression;

import java.util.ArrayList;
import java.util.List;

public class FunctionProcessor implements Processor {
    @Override
    public List<Object> process(List<Object> results) {
        FunctionExpression expression = new FunctionExpression(results);
        results = new ArrayList<>();
        results.add(expression);
        return results;
    }
}
