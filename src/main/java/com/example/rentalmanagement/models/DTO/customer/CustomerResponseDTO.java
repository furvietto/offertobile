package com.example.rentalmanagement.models.DTO.customer;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerResponseDTO {

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
