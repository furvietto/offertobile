package com.example.rentalmanagement.models.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String address;

    private String email;

    private String phone;

    private String username;

    private String password;

}
