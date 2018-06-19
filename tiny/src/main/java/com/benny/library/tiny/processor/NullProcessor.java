package com.benny.library.tiny.processor;

import java.util.List;

public class NullProcessor implements Processor {
    @Override
    public List<Object> process(List<Object> results) {
        results.clear();
        results.add(null);
        return results;
    }
}
