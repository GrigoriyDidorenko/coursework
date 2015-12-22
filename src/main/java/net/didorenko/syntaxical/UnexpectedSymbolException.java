package net.didorenko.syntaxical;

/**
 * package: net.didorenko.exception
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 18.12.2015
 */

public class UnexpectedSymbolException extends Exception {

    public UnexpectedSymbolException(String message) {
        super(message);
    }

    public UnexpectedSymbolException(String message, Throwable cause) {
        super(message, cause);
    }
}
