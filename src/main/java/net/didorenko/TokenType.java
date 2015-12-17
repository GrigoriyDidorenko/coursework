package net.didorenko;

/**
 * package: PACKAGE_NAME
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 14.12.2015
 */
public enum TokenType {

//    TYPES("int|double|string"),
    INT("int"),
    DOUBLE("double"),
    STRING("string"),
    NUMBER("-?[0-9]+"),
    //logic
    EQUAL("="),
    NOT_EQUAL("<>"),
    WRITELN("writeln"),
    READLN("readln"),
    ASSIGMENT(":="),
    MULTIPLY("*"),
    DIVIDE("/"),
    PLUS("+"),
    MINUS("-"),
    DEGREE("exp|sqr|sqrt"),
/*    METHOD("[writeln|readln]"),
    SYNTAX("[:=|,|;]"),*/
/*    MATH("[*|/|+|-]"),*/
    INITIALIZING("var"),
    CYCLE("while"),
    CONDITION("if|else"),
    BEGIN("begin"),
    END("end"),
    VARIABLE("[a-zA-Z]+-?[0-9]*"),

/*    RESERVED ("for|to|do|begin|end|var|writeln|readln|sqr|sqrt|exp|if|then"),
    WHITESPACE("[ \t\f\r\n]+")*/;
    public final String stringValue;

    private TokenType(String stringValue) {
        this.stringValue = stringValue;
    }
}
