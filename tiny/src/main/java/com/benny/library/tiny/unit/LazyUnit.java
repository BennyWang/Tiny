package com.benny.library.tiny.unit;

import com.benny.library.tiny.factory.Factory;
import com.benny.library.tiny.Result;
import com.benny.library.tiny.Token;

import java.util.List;

public class LazyUnit implements Unit {
    private Factory<? extends Unit> factory;
    private Unit unit;

    public LazyUnit(Factory<? extends Unit> factory) {
        this.factory = factory;
    }

    @Override
    public Result parse(List<Token> tokens, int position) {
        if (unit == null) {
            unit = factory.create();
        }

        return unit.parse(tokens, position);
    }
}
