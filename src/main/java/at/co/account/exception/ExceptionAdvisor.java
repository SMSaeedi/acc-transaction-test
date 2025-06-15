package at.co.account.exception;

import at.co.account.exception.responseDto.PersistentExceptionResponse;
import at.co.account.exception.responseDto.ServiceExceptionResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionAdvisor extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public PersistentExceptionResponse handleValidationExceptions(ConstraintViolationException ex) {
        List<String> details = ex.getConstraintViolations()
                .parallelStream()
                .map(e -> e.getMessage())
                .collect(Collectors.toList());

        PersistentExceptionResponse error = PersistentExceptionResponse.builder()
                .timestamp(new Date())
                .details(details)
                .build();
        return error;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ServiceExceptionResponse handleValidationExceptions(NotFoundException ex) {
        return ServiceExceptionResponse.builder()
                .timestamp(new Date())
                .details(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(NotAllowedException.class)
    public ServiceExceptionResponse notAllowedException(NotAllowedException ex) {
        return ServiceExceptionResponse.builder()
                .timestamp(new Date())
                .details(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(MismatchedInputException.class)
    public ServiceExceptionResponse mismatchedInputException(MismatchedInputException ex) {
        return ServiceExceptionResponse.builder()
                .timestamp(new Date())
                .details(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public PersistentExceptionResponse handleValidationExceptions(DataIntegrityViolationException ex) {
        List<String> strLst = new ArrayList<>();
        strLst.add(ex.getMessage());
        PersistentExceptionResponse error = PersistentExceptionResponse.builder()
                .timestamp(new Date())
                .details(strLst)
                .build();
        return error;
    }
}