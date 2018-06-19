package com.benny.library.tiny.unit;

import com.benny.library.tiny.Result;
import com.benny.library.tiny.Token;
import com.benny.library.tiny.factory.BinaryFactory;
import com.benny.library.tiny.processor.Processor;

import java.util.List;

public class RecursiveUnit implements Unit, Processor {
    private Unit unit;
    private Unit separator;

    private Result result;

    public RecursiveUnit(Unit unit, Unit separator) {
        this.unit = unit;
        this.separator = separator;
    }

    @Override
    public Result parse(final List<Token> tokens, int position) {
        Unit nextUnit = Combinator.process(Combinator.concat(separator, unit), this);
        result = unit.parse(tokens, position);
        Result nextResult = result;
        while (nextResult != null) {
            nextResult = nextUnit.parse(tokens, result.position);
            if (nextResult != null) {
                result = nextResult;
            }
        }

        return result;
    }

    @Override
    public List<Object> process(List<Object> results) {
        BinaryFactory factory = (BinaryFactory) results.get(0);
        Object right = results.get(1);
        if (factory != null) {
            // 合并函数不为空，合并两次结果
            results.clear();
            results.add(factory.create(result.results.get(0), right));
            return results;
        }

        result.results.add(right);
        return result.results;
    }
}
