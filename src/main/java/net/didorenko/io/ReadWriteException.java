package net.didorenko.io;

/**
 * package: net.didorenko.exception
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 21.12.2015
 */

public class ReadWriteException extends Exception {

    public ReadWriteException(String message) {
        super(message);
    }

    public ReadWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
