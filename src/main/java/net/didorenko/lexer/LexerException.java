package net.didorenko.lexer;

/**
 * package: net.didorenko.lexer
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 14.12.2015
 */
public class LexerException extends Exception {

    protected int lineNumber;

    public LexerException(String s, int lineNumber){
        super(s);
        this.lineNumber = lineNumber;
    }

    public LexerException(String s){
        super(s);
        this.lineNumber = 0;
    }

    @Override
    public String getMessage(){
        return new StringBuilder().append("Line number:").append(lineNumber).append("\n").append(super.getMessage()).toString();
    }
}
