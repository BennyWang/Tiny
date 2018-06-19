package com.benny.library.tiny;

import java.util.HashSet;

public class Keywords {
    private static final HashSet<String> KEYWORDS = new HashSet<>();

    public static boolean isKeyword(String name) {
        return KEYWORDS.contains(name);
    }

    public static boolean isAmbiguous(int ch) {
        return ch == '=' || ch == '?' || ch == '<' || ch == '>';
    }

    static {
        // operators
        KEYWORDS.add("=");
        KEYWORDS.add("?");
        KEYWORDS.add(":");
        KEYWORDS.add("?:");
        KEYWORDS.add("+");
        KEYWORDS.add("-");
        KEYWORDS.add("*");
        KEYWORDS.add("/");
        KEYWORDS.add(".");
        KEYWORDS.add("<");
        KEYWORDS.add("<=");
        KEYWORDS.add(">");
        KEYWORDS.add(">=");
        KEYWORDS.add("==");
        KEYWORDS.add("<>");
        KEYWORDS.add("{");
        KEYWORDS.add("}");
        KEYWORDS.add("(");
        KEYWORDS.add(")");
        KEYWORDS.add(",");

        // control flow
        KEYWORDS.add("and");
        KEYWORDS.add("or");
        KEYWORDS.add("not");
        KEYWORDS.add("if");
        KEYWORDS.add("for");
        KEYWORDS.add("in");
        KEYWORDS.add("while");

        // functions
        KEYWORDS.add("strftime");
        KEYWORDS.add("substring");
    }
}
