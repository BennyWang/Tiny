package com.benny.library.tiny.processor;

import com.benny.library.tiny.ast.FunctionExpression;
import com.benny.library.tiny.processor.Processor;

import java.util.List;

public class FunctionProcessor implements Processor {
    @Override
    public List<Object> process(List<Object> results) {
        FunctionExpression expression = new FunctionExpression(results);
        results.clear();
        results.add(expression);
        return results;
    }
}
