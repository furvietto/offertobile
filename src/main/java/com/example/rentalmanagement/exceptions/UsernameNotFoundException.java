package com.example.rentalmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UsernameNotFoundException extends RuntimeException{

    private String message;

    public UsernameNotFoundException (String message) {
        super(message);
    }
}
