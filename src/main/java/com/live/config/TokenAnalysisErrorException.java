package com.live.config;

import io.jsonwebtoken.JwtException;

public class TokenAnalysisErrorException extends JwtException {
    public TokenAnalysisErrorException(String message) {
        super(message);
    }

    public TokenAnalysisErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
