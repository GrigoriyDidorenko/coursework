package net.didorenko.semantic;

public class LogicException extends Exception {

    public LogicException(String message) {
        super(message);
    }

    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }
}
