package net.didorenko;

/**
 * package: PACKAGE_NAME
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 14.12.2015
 */
public class Token {

    private TokenType type;
    private String data;
    private int lineNumber;

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Token(TokenType type, String data, int lineNumber) {
        this.type = type;
        this.data = data;
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return String.format("(%s %s)", type.name(), data);
    }
}