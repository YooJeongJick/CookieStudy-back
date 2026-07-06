package com.example.cookiecookie.core.error.exception;


import com.example.cookiecookie.core.error.ErrorCode;

public class BadRequestException extends BusinessException {

    public BadRequestException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
