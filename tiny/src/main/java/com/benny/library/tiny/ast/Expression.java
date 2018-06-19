package com.benny.library.tiny.ast;

import com.benny.library.tiny.RuntimeContext;

public interface Expression {
    Object eval(RuntimeContext context);
}
