package com.rljj.switchswitchcommon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotAuthorizationException extends RuntimeException {
    public NotAuthorizationException(String message, String content) {
        super(message + ": " + content);
    }
}
