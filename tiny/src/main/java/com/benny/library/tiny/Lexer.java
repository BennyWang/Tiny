package com.benny.library.tiny;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    public List<Token> parse(File file) throws Exception {
        return parse(new FileInputStream(file));
    }

    public List<Token> parse(String code) throws Exception {
        return parse(new ByteArrayInputStream(code.getBytes(Charset.forName("UTF-8"))));
    }

    private List<Token> parse(InputStream stream) throws Exception {
        List<Token> tokens = new ArrayList<>();

        StreamTokenizer tokenizer = new StreamTokenizer(new InputStreamReader(stream));
        tokenizer.eolIsSignificant(true);
        tokenizer.wordChars('$', '$');
        tokenizer.wordChars('\'', '\'');
        tokenizer.wordChars('"', '"');
        tokenizer.wordChars('_', '_');

        int lineNumber = 0;
        int token;
        while ((token = tokenizer.nextToken()) != StreamTokenizer.TT_EOF) {
            double nValue = tokenizer.nval;
            String sValue = tokenizer.sval;

            switch (token) {
                case StreamTokenizer.TT_EOL:
                    tokens.add(new Token("", TokenType.SEPARATOR));
                    ++lineNumber;
                    break;
                case StreamTokenizer.TT_NUMBER:
                    tokens.add(new Token(nValue, TokenType.NUMBER));
                    break;
                case StreamTokenizer.TT_WORD:
                    if (Keywords.isKeyword(sValue)) {
                        tokens.add(new Token(sValue, TokenType.RESERVED));
                    }
                    else if (sValue.startsWith("'") && sValue.endsWith("'")) {
                        tokens.add(new Token(sValue.substring(1, sValue.length() - 1), TokenType.STRING));
                    }
                    else if (sValue.startsWith("\"") && sValue.endsWith("\"")) {
                        tokens.add(new Token(sValue.substring(1, sValue.length() - 1), TokenType.STRING));
                    }
                    else if (!sValue.contains("'") && !sValue.contains("\"")) {
                        int nextToken = tokenizer.nextToken();
                        tokenizer.pushBack();
                        if (nextToken == '(') {
                            tokens.add(new Token(sValue, TokenType.FUNCTION));
                        }
                        else {
                            tokens.add(new Token(sValue, TokenType.ID));
                        }
                    }
                    else {
                        throw new SyntaxException("Unknown keyword: " + sValue + " in line " + lineNumber);
                    }
                    break;
                default:
                    String value = String.valueOf((char) token);
                    if (Keywords.isAmbiguous(token)) {
                        String word = value + (char) tokenizer.nextToken();
                        if (Keywords.isKeyword(word)) {
                            tokens.add(new Token(word, TokenType.RESERVED));
                            break;
                        }
                        tokenizer.pushBack();
                    }

                    if (Keywords.isKeyword(value)) {
                        tokens.add(new Token(value, TokenType.RESERVED));
                    }
                    else {
                        throw new SyntaxException("Illegal character: " + (char)token + " in line " + lineNumber);
                    }
                    break;
            }
        }

        return tokens;
    }
}
