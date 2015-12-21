package net.didorenko.general;

import java.util.Arrays;

public class Keyword {

    public static final String[] TYPES = {"int", "double", "string"};
    public static final String[] RESERVED_WORDS = {"var", "begin", "if", "else", "while", "end"};
    public static final String[] METHODS = {"writeln", "read"};
    public static final String[] MATH_FUNCTIONS = {"sqrt", "exp","sqr"};
    public static final String[] ALL_WORDS = concatAll(TYPES, RESERVED_WORDS, METHODS, MATH_FUNCTIONS);
    public static final String[] MATH_OPERATIONS = {"+", "-", "*", "/"};
    public static final String[] BRACKETS = {"(", ")", "\""};
    public static final String[] BOOL_OPERATIONS = {"<", ">", "=", "!="};
    public static final String[] SYNTAX_SYMBOLS = {":=", ";", ",", ":"};

    public static final String SYMBOLS_TO_CONTINUE = "\\+|-|\\*|/|<|>|=|!=|:=|\\(";

    public static final String[] ALL_KEYWORDS = concatAll(ALL_WORDS, METHODS, MATH_OPERATIONS, BRACKETS, BOOL_OPERATIONS, SYNTAX_SYMBOLS);

    private static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    public static boolean isRegisteredWord(String str) {
        for (String string : ALL_WORDS) if (string.equals(str)) return true;
        return false;
    }

    public static boolean isType(String str) {
        for (String string : TYPES) if (string.equals(str)) return true;
        return false;
    }

    public static boolean isMethod(String str) {
        for (String string : METHODS) if (string.equals(str)) return true;
        return false;
    }

    public static boolean isMathFunction(String str) {
        for (String string : MATH_FUNCTIONS) if (string.equals(str)) return true;
        return false;
    }

}
