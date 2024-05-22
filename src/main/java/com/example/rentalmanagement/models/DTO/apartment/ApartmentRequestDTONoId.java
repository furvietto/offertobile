package com.example.rentalmanagement.models.DTO.apartment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ApartmentRequestDTONoId {

    @NotNull(message = "Address cannot be null")
    @Size(min = 1, max = 200, message = "Address must be between 1 and 200 characters")
    private String address;

    @NotNull(message = "Number of rooms cannot be null")
    @Min(value = 1, message = "Number of rooms must be at least 1")
    private Integer numberOfRooms;

    @NotNull(message = "Number of bathrooms cannot be null")
    @Min(value = 1, message = "Number of bathrooms must be at least 1")
    private Integer numberOfBathrooms;

    @NotNull(message = "Area cannot be null")
    @Positive(message = "Area must be positive")
    private Double area;

    @NotNull(message = "Price per night cannot be null")
    @Positive(message = "Price per night must be positive")
    private Double pricePerNight;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @NotNull(message = "Owner ID cannot be null")
    private Integer ownerId;

    @NotNull(message = "Event ID cannot be null")
    private Integer eventId;
}
