package com.example.cookiecookie.core.error.exception;

import com.example.cookiecookie.core.error.ErrorCode;

public class UnAuthorizedException extends BusinessException {
    public UnAuthorizedException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
