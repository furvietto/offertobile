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
public class ReservationResponseDTO {

    private Integer id;

    private Integer apartmentId;

    private CustomerEnt customer;

    private Date startDate;

    private Date endDate;

    private Double totalPayment;

    private String status;
}
