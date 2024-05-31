package com.example.rentalmanagement.models.DTO.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseDTONoIdNoFk {

    private Date startDate;

    private Date endDate;

    private Double totalPayment;

    private String status;
}
