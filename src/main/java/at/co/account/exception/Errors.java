package at.co.account.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Errors {
    CUSTOMER_NOT_FOUND("exception.customer.not.found", HttpStatus.NOT_FOUND),
    NO_ACCOUNT_TYPE_FOUND("no.account.type.found", HttpStatus.NOT_FOUND),
    NO_ACCOUNT_FOUND("exception.no.account.found", HttpStatus.NOT_FOUND),
    NO_SUCH_ACCOUNT_FOUND("no.account.found", HttpStatus.NOT_FOUND),
    INVALID_CUSTOMER("invalid.customer", HttpStatus.NOT_ACCEPTABLE),
    NOT_ALLOWED("invalid.amount.number", HttpStatus.NOT_ACCEPTABLE);

    private final String errorCode;
    private final HttpStatus httpStatus;
}
