package com.example.rentalmanagement.models.DTO.payment;

import com.example.rentalmanagement.models.entities.ReservationEnt;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class PaymentResponseDTO {

    private Integer id;

    private Date paymentDate;

    private Double amount;

    private String paymentMethod;

    private Boolean paymentStatus;

    private Integer reservationId;
}
