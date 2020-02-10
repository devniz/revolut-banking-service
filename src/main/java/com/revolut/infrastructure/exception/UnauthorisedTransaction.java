package com.revolut.infrastructure.exception;

public class UnauthorisedTransaction extends RuntimeException {
    public UnauthorisedTransaction(String message) {
        super(message);
    }
}
