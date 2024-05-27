package com.example.rentalmanagement.models.DTO.payment;

import com.example.rentalmanagement.models.entities.ReservationEnt;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTONoId {

    @NotNull(message = "Payment date cannot be null")
    @FutureOrPresent(message = "Payment date must be in the present or future")
    private Date paymentDate;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotNull(message = "Payment method cannot be null")
    @Size(min = 1, max = 50, message = "Payment method must be between 1 and 50 characters")
    private String paymentMethod;

    @NotNull(message = "Payment status cannot be null")
    private Boolean paymentStatus;

    @NotNull(message = "Reservation ID cannot be null")
    private Integer reservationId;
}
