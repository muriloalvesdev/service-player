package com.player.exception;

public class InvalidGameException extends RuntimeException {
    public InvalidGameException(final String msg) {
        super(msg);
    }
}
