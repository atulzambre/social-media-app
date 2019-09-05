package com.socialmediaapp.exception;

import com.socialmediaapp.model.ErrorResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * CustomExceptionHandler to handle the Runtime exceptions occurring in the API.
 * Designed Builder patter to create a immutable errorResponse.
 *
 * @author atulzambre
 */
@RestControllerAdvice
class SocialMediaAppExceptionHandler extends ResponseEntityExceptionHandler {

    //handleAllExceptions handles all the other exceptions occurring in the API apart from the business logic exceptions.
    //Internal Server Errors
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponseModel> handleAllExceptions(Exception ex, WebRequest web) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel.ErrorResponseExceptionBuilder(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).message(ex.getMessage()).timestamp(LocalDateTime.now()).build();
        ResponseEntity<ErrorResponseModel> handleAllExceptionResponse;
        handleAllExceptionResponse = new ResponseEntity<>(errorResponseModel, HttpStatus.INTERNAL_SERVER_ERROR);
        return handleAllExceptionResponse;
    }


    //RequestParamException handles all the exceptions related to bad request parameters.
    //BAD REQUEST
    @ExceptionHandler(CustomBadRequestException.class)
    public final ResponseEntity<ErrorResponseModel> requestParamExceptions(CustomBadRequestException ex, WebRequest web) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel.ErrorResponseExceptionBuilder(HttpStatus.BAD_REQUEST.value()).error(HttpStatus.BAD_REQUEST.toString()).message(ex.getMessage()).timestamp(LocalDateTime.now()).build();
        ResponseEntity<ErrorResponseModel> requestParamExceptionsResponse;
        requestParamExceptionsResponse = new ResponseEntity<>(errorResponseModel, HttpStatus.BAD_REQUEST);
        return requestParamExceptionsResponse;
    }

    //UserAlreadyExistsException handles all the Conflict exceptions i.e already exists in the collection type.
    //CONFLICT
    @ExceptionHandler(CustomConflictException.class)
    public final ResponseEntity<ErrorResponseModel> userAlreadyExistsExceptions(CustomConflictException ex, WebRequest web) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel.ErrorResponseExceptionBuilder(HttpStatus.CONFLICT.value()).error(HttpStatus.CONFLICT.toString()).message(ex.getMessage()).timestamp(LocalDateTime.now()).build();
        ResponseEntity<ErrorResponseModel> userAlreadyExistsExceptionsResponse;
        userAlreadyExistsExceptionsResponse = new ResponseEntity<>(errorResponseModel, HttpStatus.CONFLICT);
        return userAlreadyExistsExceptionsResponse;
    }

    //UserDoesNotExistsException handles exceptions related to data not found.
    //NOT FOUND
    @ExceptionHandler(CustomNotFoundException.class)
    public final ResponseEntity<ErrorResponseModel> userDoesNotExistsException(CustomNotFoundException ex, WebRequest web) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel.ErrorResponseExceptionBuilder(HttpStatus.NOT_FOUND.value()).error(HttpStatus.NOT_FOUND.toString()).message(ex.getMessage()).timestamp(LocalDateTime.now()).build();
        ResponseEntity<ErrorResponseModel> userDoesNotExistsExceptionResponse;
        userDoesNotExistsExceptionResponse = new ResponseEntity<>(errorResponseModel, HttpStatus.NOT_FOUND);
        return userDoesNotExistsExceptionResponse;
    }

}
