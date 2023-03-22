package com.jobis.config.exception;

import java.util.function.Supplier;

public class RegistrationNotAllowedException extends RuntimeException{

    public RegistrationNotAllowedException(String message) {
        super(message);
    }

    public RegistrationNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
