package com.enesderin.cafe_modern.exception;

public class BaseException extends RuntimeException {
    public BaseException(ErrorMessage message) {
        super(message.prepareErrorMessage());
    }
}
