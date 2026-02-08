package com.intellihire.authService.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String message)
    {
        super(message);
    }
}
