package com.benny.library.tiny.factory;

public interface UnaryFactory<T, R> {
    R create(T arg);
}
