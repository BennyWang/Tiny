package com.benny.library.tiny.unit;

import com.benny.library.tiny.Result;
import com.benny.library.tiny.Token;
import com.benny.library.tiny.TokenType;

import java.util.List;

public class TagUnit implements Unit {
    private TokenType type;

    public TagUnit(TokenType type) {
        this.type = type;
    }

    @Override
    public Result parse(List<Token> tokens, int position) {
        if (position < tokens.size()) {
            Token token = tokens.get(position);
            System.out.println("Type " + type + " parse " + token);
            if (token.type == type) {
                return new Result(position + 1, token.value);
            }
        }
        return null;
    }
}
