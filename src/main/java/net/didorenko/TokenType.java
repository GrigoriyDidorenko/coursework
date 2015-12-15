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

    TYPES("int|double|string"),
    NUMBER("-?[0-9]+"),
    //logic
    EQUALITY("[=|<>]"),
    METHODS("[writeln|readln]"),
    SYNTAX("[:=|,|;]"),
    MATH("[*|/|+|-]"),
    RESERVED ("for|to|do|begin|end|var|writeln|readln|sqr|sqrt|exp|if|then"),
    WHITESPACE("[ \t\f\r\n]+");
    public final String pattern;

    private TokenType(String pattern) {
        this.pattern = pattern;
    }
}
