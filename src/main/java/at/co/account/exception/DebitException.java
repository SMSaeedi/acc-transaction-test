package at.co.account.exception;

public class DebitException extends RuntimeException {
    public DebitException(String message, Throwable cause) {
        super(message, cause);
    }
}