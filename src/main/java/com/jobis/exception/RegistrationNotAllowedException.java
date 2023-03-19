package com.jobis.exception;

public class RegistrationNotAllowedException extends RuntimeException {

    public RegistrationNotAllowedException(String message) {
        super(message);
    }

    public RegistrationNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
