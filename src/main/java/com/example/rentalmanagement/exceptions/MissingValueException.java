package com.example.rentalmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MissingValueException extends RuntimeException{


    private String fieldvalue;

    public MissingValueException(String fieldValue) {
        super(String.format("missing this value : '%s'", fieldValue));
        this.fieldvalue = fieldValue;
    }
}
