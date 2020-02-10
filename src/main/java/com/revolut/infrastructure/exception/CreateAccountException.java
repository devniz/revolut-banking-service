package com.revolut.infrastructure.exception;

public class CreateAccountException extends RuntimeException {

    public CreateAccountException(String message) {
        super(message);
    }
}
