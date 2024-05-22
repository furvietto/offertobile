package com.example.rentalmanagement.models.DTO.photo;

import com.example.rentalmanagement.models.entities.ApartmentEnt;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PhotoResponseDTO {

    private Integer id;

    private String photo;

    private String description;

    private Integer apartmentId;
}
