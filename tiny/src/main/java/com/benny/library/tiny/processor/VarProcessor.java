package com.benny.library.tiny.processor;

import com.benny.library.tiny.ast.VarExpression;
import com.benny.library.tiny.processor.Processor;

import java.util.List;

public class VarProcessor implements Processor {
    @Override
    public List<Object> process(List<Object> results) {
        String name = (String) results.get(0);
        results.set(0, new VarExpression(name));
        return results;
    }
}
