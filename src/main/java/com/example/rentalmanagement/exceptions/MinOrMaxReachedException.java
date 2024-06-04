package com.example.rentalmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MinOrMaxReachedException extends RuntimeException{

    private String fieldvalue;

    public MinOrMaxReachedException(String fieldValue) {
        super(String.format("'%s' must be between 15 or 34", fieldValue));
        this.fieldvalue = fieldValue;
    }
}
