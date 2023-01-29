package com.abnamro.exception;

import com.abnamro.model.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@Slf4j
@ControllerAdvice
public class ReciepeRequestAdvise extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ ReciepeException.class })
    public ResponseEntity<ApiError> handleReciepeException(
            Exception ex, WebRequest request) {
        ApiError apiError = getApiError(ex);
        return new ResponseEntity<ApiError>(apiError,new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ApiError> handleGenericExceptions(
            Exception ex, WebRequest request) {
        ApiError apiError = getApiError(ex);
        return new ResponseEntity<ApiError>(apiError,new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ApiError getApiError(Exception ex) {
        ApiError apiError = new ApiError();
        log.error("Error while processing request {}.", ex.getMessage());
        if (ex instanceof ReciepeException) {
            apiError.setCode(HttpStatus.BAD_REQUEST.value());
            apiError.setMessage(ex.getMessage());
        } else {
            apiError.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            apiError.setMessage("Please contact API team. Request processing failed!");
        }
        apiError.setTimestamp(OffsetDateTime.now());
        return apiError;
    }
}
