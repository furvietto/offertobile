package com.example.rentalmanagement.models.DTO.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ReservationResponseDTO {

        private Integer id;

        private Integer apartmentId;

        private Integer customerId;

        private Date startDate;

        private Date endDate;

        private Double totalPayment;

        private String status;
    }
