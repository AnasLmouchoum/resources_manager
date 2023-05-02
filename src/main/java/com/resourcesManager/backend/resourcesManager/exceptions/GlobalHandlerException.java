package com.resourcesManager.backend.resourcesManager.exceptions;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(1)
@RestControllerAdvice
public class GlobalHandlerException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage());
        return buildResponseEntity(apiError);
    }
    @ExceptionHandler(AccountBannedException.class)
    public ResponseEntity<Object> handleAccountBannedException(AccountBannedException exception) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, exception.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<Object> handleEntityAlreadyFoundException(EntityAlreadyExistsException exception) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, exception.getMessage());
        return buildResponseEntity(apiError);
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentialsException(InvalidCredentialsException exception) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, exception.getMessage());
        return buildResponseEntity(apiError);
    }


    public ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }


}
