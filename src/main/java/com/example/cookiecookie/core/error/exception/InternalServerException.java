package com.example.cookiecookie.core.error.exception;

import com.example.cookiecookie.core.error.ErrorCode;

public class InternalServerException extends BusinessException {
    public InternalServerException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
