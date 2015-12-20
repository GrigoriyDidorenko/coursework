package net.didorenko.semantic;

public class WrongTypeException extends Exception {

    public WrongTypeException(String message) {
        super(message);
    }

    public WrongTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
