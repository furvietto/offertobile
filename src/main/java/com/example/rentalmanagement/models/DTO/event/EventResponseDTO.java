package com.example.rentalmanagement.models.DTO.event;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDTO {

    private Integer id;

    private String eventName;

    private String description;

    private Date startDate;

    private Date endDate;

    private String location;

    private String eventType;

}
