package com.player.exception;

public class LoserException extends RuntimeException {
    public LoserException() {
        super("You lost, start a new game");
    }
}
