package net.didorenko.syntaxical;

public class UnexpectedSymbolException extends Exception {

    public UnexpectedSymbolException(String message) {
        super(message);
    }

    public UnexpectedSymbolException(String message, Throwable cause) {
        super(message, cause);
    }
}
