package com.spring.clearsolutions.exception;

public class UserAgeLessThanMinimalException extends RuntimeException {
    public UserAgeLessThanMinimalException(String message) {
        super(message);
    }
}
