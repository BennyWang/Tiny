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
        tokenizer.ordinaryChar('.');
        tokenizer.ordinaryChar('/');
        tokenizer.ordinaryChar(' ');
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
                    ++lineNumber;
                    Token lastToken = tokens.get(tokens.size() - 1);
                    if ("{".equals(lastToken.value) || lastToken.type == TokenType.SEPARATOR) {
                        break;
                    }

                    int nextToken = tokenizer.nextToken();
                    tokenizer.pushBack();
                    if (nextToken == '}' || nextToken == '{') {
                        break;
                    }

                    tokens.add(new Token("", TokenType.SEPARATOR));
                    break;
                case StreamTokenizer.TT_NUMBER:
                    tokens.add(new Token(nValue, TokenType.NUMBER));
                    break;
                case StreamTokenizer.TT_WORD:
                    if (Keywords.isKeyword(sValue)) {
                        tokens.add(new Token(sValue, TokenType.RESERVED));
                    }
                    else if (sValue.startsWith("'") || sValue.startsWith("\"")) {
                        String str = readString(sValue, tokenizer);
                        if (str != null) {
                            tokens.add(new Token(str, TokenType.STRING));
                        }
                        else {
                            throw new SyntaxException("Unknown keyword: " + sValue + " in line " + lineNumber);
                        }
                    }
                    else {
                        nextToken = tokenizer.nextToken();
                        tokenizer.pushBack();
                        if (nextToken == '(') {
                            tokens.add(new Token(sValue, TokenType.FUNCTION));
                        }
                        else {
                            tokens.add(new Token(sValue, TokenType.ID));
                        }
                    }
                    break;
                default:
                    if (token != ' ') {
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
                        } else {
                            throw new SyntaxException("Illegal character: " + (char) token + " in line " + lineNumber);
                        }
                    }
                    break;
            }
        }

        removeEndSeparator(tokens);
        return tokens;
    }

    private String readString(String start, StreamTokenizer tokenizer) throws Exception {
        if ((start.startsWith("'") && start.endsWith("'")) || (start.startsWith("\"") && start.endsWith("\"")) ) {
            if (start.length() > 1) {
                return start.substring(1, start.length() - 1);
            }
        }

        String quote = start.substring(0, 1);
        StringBuilder builder = new StringBuilder();
        builder.append(start.substring(1));
        int token;
        while ((token = tokenizer.nextToken()) != StreamTokenizer.TT_EOF) {
            if (token == StreamTokenizer.TT_EOL) {
                builder.append('\n');
            }
            else if (token == StreamTokenizer.TT_NUMBER) {
                builder.append(tokenizer.nval);
            }
            else if (token == StreamTokenizer.TT_WORD) {
                String sValue = tokenizer.sval;
                if (sValue.endsWith(quote)) {
                    builder.append(sValue.substring(0, sValue.length() - 1));
                    return builder.toString();
                }
                else {
                    builder.append(tokenizer.sval);
                }
            }
            else {
                if (token == quote.charAt(0)) {
                    return builder.toString();
                }
                builder.append((char) token);
            }
        }

        return null;
    }

    private void removeEndSeparator(List<Token> tokens) {
        for (int i = tokens.size() - 1; i >= 0; --i) {
            Token token = tokens.get(i);
            if (token.type != TokenType.SEPARATOR) {
                return;
            }
            tokens.remove(i);
        }
    }
}
