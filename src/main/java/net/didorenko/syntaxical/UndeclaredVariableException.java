package net.didorenko.syntaxical;

public class UndeclaredVariableException extends Exception {

    public UndeclaredVariableException(String message) {
        super(message);
    }

    public UndeclaredVariableException(String message, Throwable cause) {
        super(message, cause);
    }
}
