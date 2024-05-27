package com.example.rentalmanagement.models.DTO.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTONoIdNoFk {

    private Date paymentDate;

    private Double amount;

    private String paymentMethod;

    private Boolean paymentStatus;

}
