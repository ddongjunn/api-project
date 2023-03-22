package com.jobis.config.exception.advice;

import com.jobis.config.exception.ErrorResult;
import com.jobis.config.exception.RegistrationNotAllowedException;
import com.jobis.config.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.jobis.controller")
public class ExceptionControllerAdvice {

    @ExceptionHandler(RegistrationNotAllowedException.class)
    public ResponseEntity<ErrorResult> RegistrationNotAllowedExHandler(RegistrationNotAllowedException e){
        ErrorResult errorResult = new ErrorResult("RegistrationNotAllowed", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResult> UserAlreadyExistsExHandler(UserAlreadyExistsException e){
        ErrorResult errorResult = new ErrorResult("UserAlreadyExists", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

}