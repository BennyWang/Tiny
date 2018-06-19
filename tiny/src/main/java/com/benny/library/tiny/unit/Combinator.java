package com.benny.library.tiny.unit;

import com.benny.library.tiny.TokenType;
import com.benny.library.tiny.processor.Processor;

public class Combinator {
    public static Unit concat(Unit... units) {
        return new ConcatUnit(units);
    }

    public static Unit alternate(Unit... units) {
        return new AlternateUnit(units);
    }

    public static Unit process(Unit unit, Processor processor) {
        return new ProcessUnit(unit, processor);
    }

    public static Unit anyKeyword(String... keywords) {
        Unit units[] = new Unit[keywords.length];
        for (int i = 0; i < keywords.length; ++i) {
            units[i] = new KeywordUnit(keywords[i], TokenType.RESERVED);
        }

        return Combinator.alternate(units);
    }
}
