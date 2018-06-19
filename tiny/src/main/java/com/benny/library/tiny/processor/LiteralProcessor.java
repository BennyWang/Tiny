package com.benny.library.tiny.processor;

import com.benny.library.tiny.ast.LiteralExpression;
import com.benny.library.tiny.processor.Processor;

import java.util.List;

public class LiteralProcessor implements Processor {
    @Override
    public List<Object> process(List<Object> results) {
        Object result = results.get(0);
        results.set(0, new LiteralExpression(result));
        return results;
    }
}
