package com.jobis.config.exception;

public class NoSuchSalaryDataException extends RuntimeException{

    public NoSuchSalaryDataException(String message) {
        super(message);
    }

    public NoSuchSalaryDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
