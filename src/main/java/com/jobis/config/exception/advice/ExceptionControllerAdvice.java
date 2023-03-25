package com.jobis.config.exception.advice;

import com.jobis.config.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.jobis.controller")
public class ExceptionControllerAdvice {

    @ExceptionHandler(RegistrationNotAllowedException.class)
    public ResponseEntity<ErrorResult> RegistrationNotAllowedExHandler(RegistrationNotAllowedException e){
        log.error("ExceptionController: "+e.getMessage());
        ErrorResult errorResult = new ErrorResult("RegistrationNotAllowed", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResult> UserAlreadyExistsExHandler(UserAlreadyExistsException e){
        log.error("ExceptionController: "+e.getMessage());
        ErrorResult errorResult = new ErrorResult("UserAlreadyExists", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResult> UsernameNotFoundExHandler(UsernameNotFoundException e){
        log.error("ExceptionController: "+e.getMessage());
        ErrorResult errorResult = new ErrorResult("UsernameNotFound", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResult> UserNotFoundExHandler(UserNotFoundException e) {
        log.error("ExceptionController: " + e.getMessage());
        ErrorResult errorResult = new ErrorResult("UsernameNotFound", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResult> exceptionAll(Exception e) {
        log.error("ExceptionController: "+e.getMessage());
        e.printStackTrace();
        ErrorResult errorResult = new ErrorResult("ServerError", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
