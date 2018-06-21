package com.benny.library.tiny.unit;

import com.benny.library.tiny.Result;
import com.benny.library.tiny.Token;
import com.benny.library.tiny.TokenType;

import java.util.List;

public class KeywordUnit implements Unit {
    private boolean nullValue;
    private String keyword;
    private TokenType type;

    public KeywordUnit(String keyword, TokenType type) {
        this(keyword, type, false);
    }

    public KeywordUnit(String keyword, TokenType type, boolean nullValue) {
        this.keyword = keyword;
        this.type = type;
        this.nullValue = nullValue;
    }

    @Override
    public Result parse(List<Token> tokens, int position) {
        if (position < tokens.size()) {
            Token token = tokens.get(position);
            System.out.println("Keyword " + keyword + " parse " + token);
            if (token.value.equals(keyword) && token.type == type) {
                if (nullValue) {
                    return new Result(position + 1, (Object[]) null);
                }
                return new Result(position + 1, token.value);
            }
        }
        return null;
    }
}
