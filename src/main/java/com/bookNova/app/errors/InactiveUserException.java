package com.bookNova.app.errors;

public class InactiveUserException extends RuntimeException {
    public InactiveUserException(String message) {
        super(message);
    }
}

