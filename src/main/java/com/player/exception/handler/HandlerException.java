package com.player.exception.handler;

import com.player.exception.EmailNotFoundException;
import com.player.exception.ExistingEmailException;
import com.player.exception.PlayerNotFoundException;
import com.player.exception.RoleNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<ResponseException> handleNotFoundException(final PlayerNotFoundException notFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createResponse(notFoundException.getMessage(),
                        HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ResponseException> handleRoleNotFoundException(final RoleNotFoundException notFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createResponse(notFoundException.getMessage(),
                        HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(ExistingEmailException.class)
    public ResponseEntity<ResponseException> handleExistingEmailException(final ExistingEmailException notFoundException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(createResponse(notFoundException.getMessage(),
                        HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ResponseException> handleEmailNotFoundException(final EmailNotFoundException notFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createResponse(notFoundException.getMessage(),
                        HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseException> handleExpiredJwtException(final ExpiredJwtException expiredJwtException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createResponse(expiredJwtException.getMessage(),
                        HttpStatus.BAD_REQUEST.value()));
    }

    private ResponseException createResponse(final String message, final int httpValue) {
        return new ResponseException(message, httpValue);
    }
}