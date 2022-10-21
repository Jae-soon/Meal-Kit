package com.js.mealkitecommerce.app.exception;

public class UserIdDuplicatedException extends RuntimeException {
    public UserIdDuplicatedException(String message) {
        super(message);
    }
}