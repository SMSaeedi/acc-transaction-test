package at.co.account.exception;

public class CreditException extends RuntimeException {
    public CreditException(String message, Throwable cause) {
        super(message, cause);
    }
}