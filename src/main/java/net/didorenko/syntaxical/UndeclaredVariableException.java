package net.didorenko.syntaxical;

/**
 * package: net.didorenko.exception
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 19.12.2015
 */

public class UndeclaredVariableException extends Exception {

    public UndeclaredVariableException(String message) {
        super(message);
    }

    public UndeclaredVariableException(String message, Throwable cause) {
        super(message, cause);
    }
}
