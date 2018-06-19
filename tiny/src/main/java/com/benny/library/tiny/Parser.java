package com.benny.library.tiny;

import com.benny.library.tiny.processor.FunctionProcessor;
import com.benny.library.tiny.processor.LiteralProcessor;
import com.benny.library.tiny.processor.NullProcessor;
import com.benny.library.tiny.processor.VarProcessor;
import com.benny.library.tiny.factory.Factory;
import com.benny.library.tiny.unit.Combinator;
import com.benny.library.tiny.unit.KeywordUnit;
import com.benny.library.tiny.unit.LazyUnit;
import com.benny.library.tiny.unit.RecursiveUnit;
import com.benny.library.tiny.unit.TagUnit;
import com.benny.library.tiny.unit.Unit;

public class Parser {
    private static Unit Number = new TagUnit(TokenType.NUMBER);
    private static Unit String = new TagUnit(TokenType.STRING);
    private static Unit Var = new TagUnit(TokenType.ID);
    private static Unit Function = new TagUnit(TokenType.FUNCTION);

    public Unit keyword(String keyword) {
        return new KeywordUnit(keyword, TokenType.RESERVED);
    }

    public Unit bool() {
        return null;
    }

    public Unit arithmetic() {
        return Combinator.concat(
                Combinator.alternate(arithmeticElement(), arithmeticGroup()),
                Combinator.anyKeyword("+", "-", "*", "/")
        );
    }

    public Unit arithmeticElement() {
        return Combinator.alternate(
                Combinator.process(Number, new LiteralProcessor()),
                Combinator.process(Var, new VarProcessor()),
                Combinator.process(function(), new FunctionProcessor()));
    }

    public Unit arithmeticGroup() {
        return Combinator.concat(keyword("("),
                new LazyUnit(new Factory<Unit>() {
                    @Override
                    public Unit create() {
                        return arithmetic();
                    }
                }),
                keyword(")"));
    }

    public Unit function() {
        return Combinator.process(
                Combinator.concat(
                        Function,
                        keyword("("),
                        params(),
                        keyword(")")),
                new FunctionProcessor());
    }

    public Unit lazyFunction() {
        return new LazyUnit(new Factory<Unit>() {
            @Override
            public Unit create() {
                return function();
            }
        });
    }

    public Unit params() {
        return new RecursiveUnit(
                Combinator.alternate(Number, String, Var, lazyFunction()),
                Combinator.process(keyword(","), new NullProcessor()));
    }

    public Unit assign() {
        return null;
    }

    public Unit statment() {
        return null;
    }

    public Unit statments() {
        return null;
    }
}
