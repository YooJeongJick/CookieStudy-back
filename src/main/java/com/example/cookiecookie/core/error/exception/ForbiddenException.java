package com.example.cookiecookie.core.error.exception;

import com.example.cookiecookie.core.error.ErrorCode;

public class ForbiddenException extends BusinessException {
    public ForbiddenException(String message,ErrorCode errorCode) {
        super(message, errorCode);
    }
}
