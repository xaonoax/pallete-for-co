package com.palleteforco.palleteforco.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyCancelledOrderExceptionHandler extends RuntimeException {
    public AlreadyCancelledOrderExceptionHandler() {
        super();
    }

    public AlreadyCancelledOrderExceptionHandler(String message) {
        super(message);
    }

    public AlreadyCancelledOrderExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyCancelledOrderExceptionHandler(Throwable cause) {
        super(cause);
    }
}
