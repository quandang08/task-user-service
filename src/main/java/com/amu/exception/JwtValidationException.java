package com.amu.exception;

import io.jsonwebtoken.JwtException;

public class JwtValidationException extends RuntimeException {
    public JwtValidationException(String message, JwtException e) {
        super(message);
    }
}
