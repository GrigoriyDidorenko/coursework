package net.didorenko.parser;

/**
 * package: net.didorenko.parser
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 17.12.2015
 */
public class SyntaxicalException extends  Exception {

    protected int lineNumber;

    public SyntaxicalException(String s, int lineNumber){
        super(s);
        this.lineNumber = lineNumber;
    }

    public SyntaxicalException(String s){
        super(s);
        this.lineNumber = 0;
    }

    @Override
    public String getMessage(){
        return new StringBuilder().append("Line number: ").append(lineNumber).append("\n").append(super.getMessage()).toString();
    }
}
