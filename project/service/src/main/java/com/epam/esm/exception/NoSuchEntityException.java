package com.epam.esm.exception;

public class NoSuchEntityException extends ServiceException {
    public NoSuchEntityException(String message) {
        super(message);
    }

    public NoSuchEntityException(String message, Throwable e) {
        super(message, e);
    }
}
