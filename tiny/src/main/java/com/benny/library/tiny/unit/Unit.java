package com.benny.library.tiny.unit;

import com.benny.library.tiny.Result;
import com.benny.library.tiny.Token;

import java.util.List;

public interface Unit {
    Result parse(List<Token> tokens, int position);
}
