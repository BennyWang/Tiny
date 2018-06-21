package com.benny.library.tiny.unit;

import com.benny.library.tiny.Result;
import com.benny.library.tiny.Token;

import java.util.List;

public class PhraseUnit implements Unit {
    private Unit unit;

    public PhraseUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public Result parse(List<Token> tokens, int position) {
        Result result = unit.parse(tokens, position);
        if (result != null && result.position == tokens.size()) {
            return result;
        }

        if (result == null) {
            throw new RuntimeException("parse token failed: " + tokens);
        }
        throw new RuntimeException("unknown token: " + tokens.get(result.position));
    }
}
