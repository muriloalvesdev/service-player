package com.player.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(final String message) {
        super(message);
    }
}

