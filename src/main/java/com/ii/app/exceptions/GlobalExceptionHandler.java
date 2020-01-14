package com.ii.app.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.naming.AuthenticationException;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;
    private static final String UNEXPECTED_ERROR = "Exception.unexpected";
    private static final String AUTHENTICATION_ERROR = "Exception.authentication";

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgs(ApiException exception, Locale locale) {
        String errMessage = this.messageSource.getMessage(exception.getMessage(), exception.getArgs(), locale);
        return new ResponseEntity<>(new ApiResponse(errMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleInvalidArgs(MethodArgumentNotValidException exception, Locale locale) {
        BindingResult bindingResult = exception.getBindingResult();
        List<String> errMessages = bindingResult.getAllErrors()
            .stream()
            .map(e -> messageSource.getMessage(e, locale))
            .collect(Collectors.toList());
        return new ResponseEntity<>(new ApiResponse(errMessages), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleExceptions(Exception exception, Locale locale) {
        String errMessage;
        if (exception instanceof AccessDeniedException) {
            errMessage = messageSource.getMessage(AUTHENTICATION_ERROR, null, locale);
            return new ResponseEntity<>(new ApiResponse(errMessage), HttpStatus.FORBIDDEN);
        }        errMessage = messageSource.getMessage(UNEXPECTED_ERROR, null, locale);

        System.out.println(exception.getMessage());
        return new ResponseEntity<>(new ApiResponse(errMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}