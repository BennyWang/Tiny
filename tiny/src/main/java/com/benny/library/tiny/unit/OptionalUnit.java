package com.benny.library.tiny.unit;

import com.benny.library.tiny.Result;
import com.benny.library.tiny.Token;

import java.util.List;

public class OptionalUnit implements Unit {
    private Unit unit;
    public OptionalUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public Result parse(List<Token> tokens, int position) {
        Result result = unit.parse(tokens, position);
        if (result != null) {
            return result;
        }

        return new Result(position, (Object[]) null);
    }
}
