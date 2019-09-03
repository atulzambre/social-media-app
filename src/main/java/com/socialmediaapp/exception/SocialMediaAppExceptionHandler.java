package com.socialmediaapp.exception;

import com.socialmediaapp.model.ErrorResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class SocialMediaAppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleAllExceptions(Exception ex, WebRequest web) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel.ErrorResponseExceptionBuilder(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).message(ex.getMessage()).timestamp(LocalDateTime.now()).build();
        return new ResponseEntity(errorResponseModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RequestParamException.class)
    public final ResponseEntity requestParamlExceptions(RequestParamException ex, WebRequest web) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel.ErrorResponseExceptionBuilder(HttpStatus.BAD_REQUEST.value()).error(HttpStatus.BAD_REQUEST.toString()).message(ex.getMessage()).timestamp(LocalDateTime.now()).build();
        return new ResponseEntity(errorResponseModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public final ResponseEntity userAlreadyExistsExceptions(UserAlreadyExistsException ex, WebRequest web) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel.ErrorResponseExceptionBuilder(HttpStatus.CONFLICT.value()).error(HttpStatus.CONFLICT.toString()).message(ex.getMessage()).timestamp(LocalDateTime.now()).build();
        return new ResponseEntity(errorResponseModel, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserDoesNotExistsException.class)
    public final ResponseEntity userDoesNotExistsException(UserDoesNotExistsException ex, WebRequest web) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel.ErrorResponseExceptionBuilder(HttpStatus.NOT_FOUND.value()).error(HttpStatus.NOT_FOUND.toString()).message(ex.getMessage()).timestamp(LocalDateTime.now()).build();
        return new ResponseEntity(errorResponseModel, HttpStatus.NOT_FOUND);
    }
}
