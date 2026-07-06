package com.example.cookiecookie.core.error.exception;

import com.example.cookiecookie.core.error.ErrorCode;

public class InvalidTokenException extends BusinessException {
    public InvalidTokenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
