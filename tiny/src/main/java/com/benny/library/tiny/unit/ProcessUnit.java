package com.benny.library.tiny.unit;

import com.benny.library.tiny.Result;
import com.benny.library.tiny.Token;
import com.benny.library.tiny.processor.Processor;

import java.util.List;

public class ProcessUnit implements Unit {
    private Unit unit;
    private Processor processor;

    public ProcessUnit(Unit unit, Processor processor) {
        this.unit = unit;
        this.processor = processor;
    }

    @Override
    public Result parse(List<Token> tokens, int position) {
        Result result = unit.parse(tokens, position);
        if (result != null) {
            result.results = processor.process(result.results);
        }
        return result;
    }
}
