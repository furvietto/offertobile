package com.example.rentalmanagement.models.DTO.apartment;

import lombok.Data;

@Data
public class ApartmentResponseDTONoIdNoFk {

    private String address;

    private Integer numberOfRooms;

    private Integer numberOfBathrooms;

    private Double area;

    private Double pricePerNight;

    private String description;

}
