package com.bookNova.app.errors;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
