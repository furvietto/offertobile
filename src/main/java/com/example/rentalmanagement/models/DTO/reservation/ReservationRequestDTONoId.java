package com.example.rentalmanagement.models.DTO.reservation;

import com.example.rentalmanagement.models.entities.ApartmentEnt;
import com.example.rentalmanagement.models.entities.CustomerEnt;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationRequestDTONoId {

    @NotNull(message = "Apartment ID cannot be null")
    private ApartmentEnt apartment;

    @NotNull(message = "Customer ID cannot be null")
    private Integer customerId;

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private Date startDate;

    @NotNull(message = "End date cannot be null")
    @FutureOrPresent(message = "End date must be in the present or future")
    private Date endDate;

    @NotNull(message = "Total payment cannot be null")
    @Positive(message = "Total payment must be positive")
    private Double totalPayment;

    @NotNull(message = "Status cannot be null")
    @Size(min = 1, max = 50, message = "Status must be between 1 and 50 characters")
    private String status;
}
