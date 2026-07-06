package com.example.cookiecookie.core.error.exception;

import com.example.cookiecookie.core.error.ErrorCode;

public class S3Exception extends BusinessException {
    public S3Exception(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
