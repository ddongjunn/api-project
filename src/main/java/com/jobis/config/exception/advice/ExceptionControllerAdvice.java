package com.jobis.config.exception.advice;

import com.jobis.config.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.jobis.controller")
public class ExceptionControllerAdvice {

    @ExceptionHandler(RegistrationNotAllowedException.class)
    public ResponseEntity<ErrorResult> registrationNotAllowedExHandler(RegistrationNotAllowedException e){
        log.error("ExceptionController: "+e.getMessage());
        ErrorResult errorResult = new ErrorResult("RegistrationNotAllowed", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResult> userAlreadyExistsExHandler(UserAlreadyExistsException e){
        log.error("ExceptionController: "+e.getMessage());
        ErrorResult errorResult = new ErrorResult("UserAlreadyExists", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResult> usernameNotFoundExHandler(UsernameNotFoundException e){
        log.error("ExceptionController: "+e.getMessage());
        ErrorResult errorResult = new ErrorResult("UsernameNotFound", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResult> userNotFoundExHandler(UserNotFoundException e) {
        log.error("ExceptionController: " + e.getMessage());
        ErrorResult errorResult = new ErrorResult("UsernameNotFound", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResult> badCredentialsExHandler(BadCredentialsException e) {
        log.error("ExceptionController: " + e.getMessage());
        ErrorResult errorResult = new ErrorResult("BadCredentials", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchSalaryDataException.class)
    public ResponseEntity<ErrorResult> noSuchSalaryDataExHandler(NoSuchSalaryDataException e) {
        log.error("ExceptionController: " + e.getMessage());
        ErrorResult errorResult = new ErrorResult("NoSuchSalaryData", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> methodArgumentNotValidExHandler(MethodArgumentNotValidException e){
        ErrorResult errorResult = new ErrorResult("MethodArgumentNotValid", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResult> exceptionAll(Exception e) {
        ErrorResult errorResult = new ErrorResult("ServerError", e.getMessage());
        log.error("ExceptionController: "+e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
