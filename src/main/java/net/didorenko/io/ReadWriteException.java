package net.didorenko.io;

public class ReadWriteException extends Exception {

    public ReadWriteException(String message) {
        super(message);
    }

    public ReadWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
