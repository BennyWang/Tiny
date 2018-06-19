package com.benny.library.tiny.unit;

import com.benny.library.tiny.Result;
import com.benny.library.tiny.Token;

import java.util.List;

public class AlternateUnit implements Unit {
    private Unit[] units;

    public AlternateUnit(Unit[] units) {
        this.units = units;
    }

    @Override
    public Result parse(List<Token> tokens, int position) {
        for (Unit unit : units) {
            Result result = unit.parse(tokens, position);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
