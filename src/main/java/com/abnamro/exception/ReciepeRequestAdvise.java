package com.abnamro.exception;

import com.abnamro.model.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class ReciepeRequestAdvise extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ ReciepeException.class })
    public ResponseEntity<ApiError> handleReciepeException(
            Exception ex, WebRequest request) {
        ApiError apiError = getApiError(ex);
        return new ResponseEntity<ApiError>(apiError,new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private static ApiError getApiError(Exception ex) {
        ApiError apiError = new ApiError();
        apiError.setCode(HttpStatus.BAD_REQUEST.value());
        apiError.setMessage(ex.getMessage());
        apiError.setTimestamp(OffsetDateTime.now());
        return apiError;
    }
}
