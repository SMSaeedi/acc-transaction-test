package at.co.account.exception;

public class NotFoundException extends RuntimeException {
    Errors errors;
    public NotFoundException(Errors notFound) {
        super(notFound.getErrorCode());
        this.errors = notFound;
    }
}