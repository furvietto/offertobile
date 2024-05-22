package com.example.rentalmanagement.models.DTO.event;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class EventRequestDTONoId {

    @NotNull(message = "Event name cannot be null")
    @Size(min = 1, max = 200, message = "Event name must be between 1 and 200 characters")
    private String eventName;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private Date startDate;

    @NotNull(message = "End date cannot be null")
    @FutureOrPresent(message = "End date must be in the present or future")
    private Date endDate;

    @NotNull(message = "Location cannot be null")
    @Size(min = 1, max = 200, message = "Location must be between 1 and 200 characters")
    private String location;

    @NotNull(message = "Event type cannot be null")
    @Size(min = 1, max = 100, message = "Event type must be between 1 and 100 characters")
    private String eventType;
}
