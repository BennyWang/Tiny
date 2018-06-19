package com.benny.library.tiny.unit;

import com.benny.library.tiny.Result;
import com.benny.library.tiny.Token;
import com.benny.library.tiny.TokenType;

import java.util.List;

public class KeywordUnit implements Unit {
    private String keyword;
    private TokenType type;

    public KeywordUnit(String keyword, TokenType type) {
        this.keyword = keyword;
        this.type = type;
    }

    @Override
    public Result parse(List<Token> tokens, int position) {
        Token token = tokens.get(position);
        if (token.value.equals(keyword) && token.type == type) {
            return new Result(position + 1, (String) token.value);
        }
        return null;
    }
}
