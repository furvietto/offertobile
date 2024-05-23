package com.example.rentalmanagement.models.DTO.apartment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentResponseDTONoIdNoFk {

    private String address;

    private Integer numberOfRooms;

    private Integer numberOfBathrooms;

    private Double area;

    private Double pricePerNight;

    private String description;

}
