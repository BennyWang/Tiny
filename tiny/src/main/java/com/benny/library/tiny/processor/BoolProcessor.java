package com.benny.library.tiny.processor;

import com.benny.library.tiny.ast.Expression;
import com.benny.library.tiny.util.ValueConvertUtil;

import java.util.List;

public class BoolProcessor implements Processor {
    @Override
    public List<Object> process(List<Object> results) {
        Object result = results.get(0);
        if (!(result instanceof Expression)) {
            results.set(0, ValueConvertUtil.convertBoolean(result));
        }
        return results;
    }
}
