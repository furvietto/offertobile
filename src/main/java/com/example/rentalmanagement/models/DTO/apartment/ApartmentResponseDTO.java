package com.example.rentalmanagement.models.DTO.apartment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ApartmentResponseDTO {

    private Integer id;

    private String address;

    private Integer numberOfRooms;

    private Integer numberOfBathrooms;

    private Double area;

    private Double pricePerNight;

    private String description;

    private Integer ownerId;

    private Integer eventId;
}
