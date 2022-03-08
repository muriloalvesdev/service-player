package com.player.exception.handler;

import com.player.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<ResponseException> handle(final PlayerNotFoundException notFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createResponse(notFoundException.getMessage(),
                        HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ResponseException> handle(final RoleNotFoundException notFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createResponse(notFoundException.getMessage(),
                        HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(ExistingEmailException.class)
    public ResponseEntity<ResponseException> handle(final ExistingEmailException notFoundException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(createResponse(notFoundException.getMessage(),
                        HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ResponseException> handle(final EmailNotFoundException notFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createResponse(notFoundException.getMessage(),
                        HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseException> handle(final ExpiredJwtException expiredJwtException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createResponse(expiredJwtException.getMessage(),
                        HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(LoserException.class)
    public ResponseEntity<ResponseException> handle(final LoserException loserException) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(createResponse(loserException.getMessage(),
                        HttpStatus.NO_CONTENT.value()));
    }

    @ExceptionHandler(InvalidGameException.class)
    public ResponseEntity<ResponseException> handle(final InvalidGameException invalidGameException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createResponse(invalidGameException.getMessage(),
                        HttpStatus.BAD_REQUEST.value()));
    }

    private ResponseException createResponse(final String message, final int httpValue) {
        return new ResponseException(message, httpValue);
    }
}