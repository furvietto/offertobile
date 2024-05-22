package com.example.rentalmanagement.models.DTO.photo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PhotoRequestDTONoId {

    @NotNull(message = "Photo cannot be null")
    @Size(min = 1, max = 500, message = "Photo must be between 1 and 500 characters")
    private String photo;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @NotNull(message = "Apartment ID cannot be null")
    private Integer apartmentId;
}
