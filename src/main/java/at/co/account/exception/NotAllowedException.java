package at.co.account.exception;

public class NotAllowedException extends RuntimeException {
    Errors errors;
    public NotAllowedException(Errors notAllowed) {
        super(notAllowed.getErrorCode());
        this.errors = notAllowed;
    }
}