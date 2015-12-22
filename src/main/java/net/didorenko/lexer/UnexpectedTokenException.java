package net.didorenko.lexer;

/**
 * package: net.didorenko.lexer
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 17.12.2015
 */
public class UnexpectedTokenException extends Exception {

    public UnexpectedTokenException(String message) {
        super(message);
    }

    public UnexpectedTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
