package com.example.cookiecookie.core.error.exception;


import com.example.cookiecookie.core.error.ErrorCode;

public class DuplicateException extends BusinessException {

    public DuplicateException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
