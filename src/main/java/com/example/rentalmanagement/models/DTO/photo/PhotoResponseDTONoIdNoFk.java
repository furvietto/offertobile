package com.example.rentalmanagement.models.DTO.photo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoResponseDTONoIdNoFk {

    private String photo;

    private String description;

}
