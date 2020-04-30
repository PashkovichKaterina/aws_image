package com.epam.esm.exception;

public class EntityIsAlreadyExistsException extends ServiceException {
    public EntityIsAlreadyExistsException(String message) {
        super(message);
    }

    public EntityIsAlreadyExistsException(String message, Throwable e) {
        super(message, e);
    }
}
