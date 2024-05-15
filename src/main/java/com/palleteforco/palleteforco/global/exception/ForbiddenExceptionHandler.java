package com.palleteforco.palleteforco.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenExceptionHandler extends RuntimeException {
    public ForbiddenExceptionHandler() {
        super();
    }

    public ForbiddenExceptionHandler(String message) {
        super(message);
    }

    public ForbiddenExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenExceptionHandler(Throwable cause) {
        super(cause);
    }
}
