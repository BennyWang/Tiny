package com.benny.library.tiny;

import com.benny.library.tiny.ast.ArithmeticExpression;
import com.benny.library.tiny.ast.AssignExpression;
import com.benny.library.tiny.ast.BoolExpression;
import com.benny.library.tiny.ast.BoolOperatorExpression;
import com.benny.library.tiny.ast.Expression;
import com.benny.library.tiny.ast.IfExpression;
import com.benny.library.tiny.ast.NotExpression;
import com.benny.library.tiny.ast.NullElseExpression;
import com.benny.library.tiny.ast.StatementsExpression;
import com.benny.library.tiny.ast.WhileExpression;
import com.benny.library.tiny.factory.BinaryFactory;
import com.benny.library.tiny.factory.UnaryFactory;
import com.benny.library.tiny.processor.BoolProcessor;
import com.benny.library.tiny.ast.ConnectStringExpression;
import com.benny.library.tiny.processor.FunctionProcessor;
import com.benny.library.tiny.processor.NullProcessor;
import com.benny.library.tiny.processor.Processor;
import com.benny.library.tiny.processor.VarProcessor;
import com.benny.library.tiny.unit.Combinator;
import com.benny.library.tiny.unit.KeywordUnit;
import com.benny.library.tiny.unit.LazyUnit;
import com.benny.library.tiny.unit.OptionalUnit;
import com.benny.library.tiny.unit.TagUnit;
import com.benny.library.tiny.unit.Unit;

import java.util.ArrayList;

public class Parser {
    private static String[][] ARITHMETIC_PRECEDENCE_LEVELS = new String[][] {
            new String[] { "*", "/" },
            new String[] { "+", "-" }
    };

    private static String[][] BOOL_PRECEDENCE_LEVELS = new String[][] {
            new String[] { "and" },
            new String[] { "or" }
    };

    private static Unit Number = new TagUnit(TokenType.NUMBER);
    private static Unit String = new TagUnit(TokenType.STRING);
    private static Unit Var = new TagUnit(TokenType.ID);
    private static Unit Function = new TagUnit(TokenType.FUNCTION);
    private static Unit Separator = new TagUnit(TokenType.SEPARATOR);

    private Unit keyword(String keyword) {
        return new KeywordUnit(keyword, TokenType.RESERVED);
    }

    private Unit keyword(String keyword, boolean nullValue) {
        return new KeywordUnit(keyword, TokenType.RESERVED, nullValue);
    }

    public Unit connectString() {
        return Combinator.process(
                Combinator.recurse(
                        Combinator.alternate(
                                String,
                                Combinator.process(Var, new VarProcessor()),
                                function()
                        ),
                        Combinator.process(keyword("."), new NullProcessor())
                ),
                results -> {
                    ConnectStringExpression expression = new ConnectStringExpression(results);
                    results = new ArrayList<>();
                    results.add(expression);
                    return results;
                }
        );
    }

    public Unit bool() {
        return precedence(
                boolTerm(),
                BOOL_PRECEDENCE_LEVELS,
                results -> {
                    final String operator = (String) results.get(0);
                    BinaryFactory factory = (arg1, arg2) -> new BoolOperatorExpression(operator, arg1, arg2);
                    results.set(0, factory);
                    return results;
                }
        );
    }

    private Unit boolTerm() {
        return Combinator.alternate(
                boolNotElement(),
                boolOperatorElement(),
                Combinator.process(boolElement(), new BoolProcessor()),
                boolGroup()
        );
    }

    private Unit boolElement() {
        return Combinator.alternate(
                String,
                arithmeticElement()
        );
    }

    private Unit boolNotElement() {
        return Combinator.process(
                Combinator.concat(
                        keyword("not", true),
                        new LazyUnit(this::boolTerm)),
                results -> {
                    results.set(0, new NotExpression(results.get(0)));
                    return results;
                }
        );
    }

    private Unit boolOperatorElement() {
        return Combinator.process(
                Combinator.concat(
                        boolElement(),
                        Combinator.anyKeyword("<", "<=", ">", ">=", "==", "<>"),
                        boolElement()
                ),
                results -> {
                    BoolExpression expression = new BoolExpression((String) results.get(1), results.get(0), results.get(2));
                    results.clear();
                    results.add(expression);
                    return results;
                }
        );
    }

    private Unit boolGroup() {
        return Combinator.concat(
                keyword("(", true),
                new LazyUnit(this::bool),
                keyword(")", true));
    }

    public Unit arithmetic() {
        return precedence(
                Combinator.alternate(arithmeticElement(), arithmeticGroup()),
                ARITHMETIC_PRECEDENCE_LEVELS,
                results -> {
                    final String operator = (String) results.get(0);
                    BinaryFactory factory = (arg1, arg2) -> new ArithmeticExpression(operator, arg1, arg2);
                    results.set(0, factory);
                    return results;
                }
        );
    }

    private Unit arithmeticElement() {
        return Combinator.alternate(
                Number,
                Combinator.process(Var, new VarProcessor()),
                function());
    }

    private Unit arithmeticGroup() {
        return Combinator.concat(
                keyword("(", true),
                new LazyUnit(this::arithmetic),
                keyword(")", true));
    }

    public Unit function() {
        return Combinator.process(
                Combinator.concat(
                        Function,
                        keyword("(", true),
                        params(),
                        keyword(")", true)),
                new FunctionProcessor());
    }

    private Unit params() {
        return Combinator.recurse(
                Combinator.alternate(
                        new LazyUnit(this::arithmetic),
                        new LazyUnit(this::connectString),
                        Number,
                        String,
                        Combinator.process(Var, new VarProcessor()),
                        new LazyUnit(this::function)),
                Combinator.process(keyword(","), new NullProcessor()));
    }

    public Unit assignStatement() {
        return Combinator.process(
                Combinator.concat(
                        Var,
                        keyword("=", true),
                        Combinator.alternate(
                                tripleStatement(),
                                nullElseStatement(),
                                arithmetic(),
                                function(),
                                bool(),
                                connectString()
                        )),
                results -> {
                    Expression expression = new AssignExpression((String) results.get(0), results.get(1));
                    results.clear();
                    results.add(expression);
                    return results;
                });
    }

    public Unit ifStatement() {
        return Combinator.process(
                Combinator.concat(
                        keyword("if", true),
                        keyword("(", true),
                        bool(),
                        keyword(")", true),
                        keyword("{", true),
                        new LazyUnit(this::statements),
                        keyword("}", true),
                        new OptionalUnit(
                                Combinator.concat(
                                        keyword("else", true),
                                        keyword("{", true),
                                        new LazyUnit(this::statements),
                                        keyword("}", true)
                                )
                        )
                ),
                results -> {
                    IfExpression expression = new IfExpression(
                            results.get(0),
                            results.get(1),
                            results.size() < 3 ? null : results.get(2));
                    results.clear();
                    results.add(expression);
                    return results;
                }
        );
    }

    public Unit whileStatement() {
        return Combinator.process(
                Combinator.concat(
                        keyword("while", true),
                        keyword("(", true),
                        bool(),
                        keyword(")", true),
                        keyword("{", true),
                        new LazyUnit(this::statements),
                        keyword("}", true)
                ),
                results -> {
                    WhileExpression expression = new WhileExpression(results.get(0), results.get(1));
                    results.clear();
                    results.add(expression);
                    return results;
                }
        );
    }

    Unit tripleStatement() {
        return Combinator.process(
                Combinator.concat(
                        bool(),
                        keyword("?", true),
                        Combinator.alternate(
                                new LazyUnit(this::statement),
                                String,
                                Number,
                                Var),
                        keyword(":", true),
                        Combinator.alternate(
                                new LazyUnit(this::statement),
                                String,
                                Number,
                                Var)
                ),
                results -> {
                    IfExpression expression = new IfExpression(
                            results.get(0),
                            results.get(1),
                            results.get(2));
                    results.clear();
                    results.add(expression);
                    return results;
                }
        );
    }

    Unit nullElseStatement() {
        return Combinator.process(
                Combinator.concat(
                        bool(),
                        keyword("?:", true),
                        Combinator.alternate(
                                new LazyUnit(this::statement),
                                String,
                                Number,
                                Var)
                ),
                results -> {
                    NullElseExpression expression = new NullElseExpression(
                            results.get(0),
                            results.get(1));
                    results.clear();
                    results.add(expression);
                    return results;
                }
        );
    }

    private Unit statement() {
        return Combinator.alternate(
                assignStatement(),
                ifStatement(),
                whileStatement(),
                function(),
                tripleStatement(),
                nullElseStatement()
        );
    }

    public Unit statements() {
        return Combinator.process(
                Combinator.recurse(
                        statement(),
                        Combinator.process(Separator, new NullProcessor())),
                results -> {
                    StatementsExpression expression = new StatementsExpression(results);
                    results = new ArrayList<>();
                    results.add(expression);
                    return results;
                }
        );
    }

    private Unit precedence(Unit unit, String[][] levels, Processor processor) {
        UnaryFactory<String[], Unit> factory = args -> Combinator.process(
                Combinator.anyKeyword(args),
                processor
        );

        for (String[] level : levels) {
            unit = Combinator.recurse(unit, factory.create(level));
        }

        return unit;
    }
}
