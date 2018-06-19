package com.benny.library.tiny.unit;

import com.benny.library.tiny.Result;
import com.benny.library.tiny.Token;

import java.util.List;

public class ConcatUnit implements Unit {
    private Unit[] units;

    public ConcatUnit(Unit[] units) {
        this.units = units;
    }

    @Override
    public Result parse(List<Token> tokens, int position) {
        Result result = null;
        for (Unit unit : units) {
            Result step = unit.parse(tokens, result == null ? position : result.position);
            if (step == null) {
                return null;
            }

            if (result == null) {
                result = step;
            }
            else {
                result.results.addAll(step.results);
                result.position = step.position;
            }
        }

        return result;
    }
}
