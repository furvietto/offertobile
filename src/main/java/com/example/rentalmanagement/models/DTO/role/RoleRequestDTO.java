package com.example.rentalmanagement.models.DTO.role;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequestDTO {

    @NotNull(message = "Role cannot be null")
    @Size(min = 1, max = 50, message = "Role must be between 1 and 50 characters")
    private String role;
}
