package com.abnamro.exception;

import lombok.Getter;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;

public class ReciepeException extends NestedRuntimeException {

    public ReciepeException(String msg) {
        super(msg);
    }

    public ReciepeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ReciepeException(String msg, Throwable throwable, HttpStatus httpStatus) {
        super(msg,throwable);
    }
}
