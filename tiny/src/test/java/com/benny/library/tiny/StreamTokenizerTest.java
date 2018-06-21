package com.benny.library.tiny;

import com.benny.library.tiny.ast.Expression;
import com.benny.library.tiny.unit.PhraseUnit;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StreamTokenizerTest {
    @Test
    public void testArithmetic() throws Exception {
        File file = new File("src/test/java/resources/arithmetic_sample.tiny");
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.parse(file);

        Parser parser = new Parser();
        Result result = parser.arithmetic().parse(tokens, 0);
        Expression expression = (Expression) result.results.get(0);
        assertEquals(12.0, expression.eval(new RuntimeContext()));
    }

    @Test
    public void testBool() throws Exception {
        File file = new File("src/test/java/resources/bool_sample.tiny");
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.parse(file);

        Parser parser = new Parser();
        Result result = parser.bool().parse(tokens, 0);
        System.out.println(result);
        Expression expression = (Expression) result.results.get(0);
        assertEquals(true, expression.eval(new RuntimeContext()));
    }

    @Test
    public void testConnectString() throws Exception {
        File file = new File("src/test/java/resources/connect_string_sample.tiny");
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.parse(file);

        Parser parser = new Parser();
        Result result = parser.connectString().parse(tokens, 0);
        Expression expression = (Expression) result.results.get(0);
        assertEquals("Hello null World", expression.eval(new RuntimeContext()));
    }

    @Test
    public void testIfStatement() throws Exception {
        File file = new File("src/test/java/resources/if_sample.tiny");
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.parse(file);
        Parser parser = new Parser();
        Result result = parser.ifStatement().parse(tokens, 0);
        Expression expression = (Expression) result.results.get(0);
        expression.eval(new RuntimeContext());
    }

    @Test
    public void testWhileStatement() throws Exception {
        File file = new File("src/test/java/resources/while_sample.tiny");
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.parse(file);
        Parser parser = new Parser();
        Result result = parser.whileStatement().parse(tokens, 0);
        serializeObject(result.results);

        List<Object> results = readObject(new File("/Users/benny/Projects/ast.tiny"));
        Expression expression = (Expression) results.get(0);
        Map<String, Object> vars = new HashMap<>();
        vars.put("$i", 3);
        expression.eval(new RuntimeContext(vars, new HashMap<>()));
    }

    @Test
    public void testTripleStatement() throws Exception {
        File file = new File("src/test/java/resources/triple_sample.tiny");
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.parse(file);
        Parser parser = new Parser();
        Result result = parser.tripleStatement().parse(tokens, 0);
        Expression expression = (Expression) result.results.get(0);
        assertEquals("I'm right", expression.eval(new RuntimeContext()));
    }

    @Test
    public void testNullElseStatement() throws Exception {
        File file = new File("src/test/java/resources/null_else_sample.tiny");
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.parse(file);
        Parser parser = new Parser();
        Result result = parser.nullElseStatement().parse(tokens, 0);
        Expression expression = (Expression) result.results.get(0);
        Map<String, Object> vars = new HashMap<>();
        Map<String, Object> globals = new HashMap<>();
        assertEquals("ddd", expression.eval(new RuntimeContext(vars, globals)));
        globals.put("addd", "hhh");
        assertEquals("hhh", expression.eval(new RuntimeContext(vars, globals)));
    }

    private void serializeObject(Object object) throws Exception {
        FileOutputStream fileOut = new FileOutputStream("/Users/benny/Projects/ast.tiny");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(object);
        out.close();
        fileOut.close();
    }

    private <T> T readObject(File file) throws Exception {
        FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        T result = (T) in.readObject();
        in.close();
        fileIn.close();
        return result;
    }

    @Test
    public void testPhraseStatement() throws Exception {
        File file = new File("src/test/java/resources/phrase_sample.tiny");
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.parse(file);
        Parser parser = new Parser();
        Result result = new PhraseUnit(parser.statements()).parse(tokens, 0);
        Expression expression = (Expression) result.results.get(0);
        expression.eval(new RuntimeContext());
    }
}
