package com.example.rentalmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MaxCharacterReachedException extends RuntimeException{

    private String fieldvalue;

    public MaxCharacterReachedException(String fieldValue) {
        super(String.format("'%s' must be less than 500 characters", fieldValue));
        this.fieldvalue = fieldValue;
    }
}
