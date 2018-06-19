package com.benny.library.tiny;

public class Token {
    public Object value;

    public TokenType type;

    public Token(Object value, TokenType type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        return value + "->" + type;
    }
}
