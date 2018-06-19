package com.benny.library.tiny;

import com.benny.library.tiny.ast.Expression;

import org.junit.Test;

import java.io.File;
import java.util.List;

public class StreamTokenizerTest {

    @Test
    public void testTokenizer() throws Exception {
        File file = new File("src/test/java/resources/sample.tiny");
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.parse(file);
        System.out.println(tokens);
    }

    @Test
    public void testParser() throws Exception {
        File file = new File("src/test/java/resources/sample.tiny");
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.parse(file);

        Parser parser = new Parser();
        Result result = parser.function().parse(tokens, 0);
        System.out.println(result);
        Expression expression = (Expression) result.results.get(0);
        expression.eval(new RuntimeContext());
    }
}
