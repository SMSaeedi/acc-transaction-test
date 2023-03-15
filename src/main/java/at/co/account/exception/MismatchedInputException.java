package at.co.account.exception;

public class MismatchedInputException extends RuntimeException {
    Errors errors;
    public MismatchedInputException(Errors notFound) {
        super(notFound.getErrorCode());
        this.errors = notFound;
    }
}