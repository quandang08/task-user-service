package com.amu.exception;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtExpiredException extends RuntimeException {
    public JwtExpiredException(String message, ExpiredJwtException e) {
        super(message);
    }
}
