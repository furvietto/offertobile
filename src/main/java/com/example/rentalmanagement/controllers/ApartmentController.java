package com.example.rentalmanagement.controllers;

import com.example.rentalmanagement.exceptions.ErrorDetails;
import com.example.rentalmanagement.exceptions.ResourceNotFoundException;
import com.example.rentalmanagement.models.DTO.apartment.ApartmentRequestDTONoId;
import com.example.rentalmanagement.models.DTO.apartment.ApartmentResponseDTO;
import com.example.rentalmanagement.models.DTO.event.EventRequestDTONoId;
import com.example.rentalmanagement.models.DTO.event.EventResponseDTO;
import com.example.rentalmanagement.models.DTO.photo.PhotoResponseDTONoIdNoFk;
import com.example.rentalmanagement.models.DTO.reservation.ReservationResponseDTONoIdNoFk;
import com.example.rentalmanagement.services.ApartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/v1/apartments")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Apartment Management", description = "Operations pertaining to apartments in the Rental Management System")
public class ApartmentController {

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService){
        this.apartmentService = apartmentService;
    };

    // Apartment APIs
    /**
     * Retrieve all apartments.
     * @return List of all apartments.
     */
    @Operation(summary = "Retrieve all apartments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the apartments",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApartmentResponseDTO.class)) })
    })
    @RequestMapping(
            path = "/getAllApartments",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ApartmentResponseDTO>> getAllApartments() {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.findAll());
    }

    /**
     * Retrieve an apartment by its ID.
     * @param id ID of the apartment.
     * @return Apartment details.
     */
    @Operation(summary = "Retrieve an apartment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the apartment",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApartmentResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Apartment not found",
                    content = @Content)
    })
    @RequestMapping(
            path = "/getApartmentById/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApartmentResponseDTO> getApartment(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.findById(id));
    }

    /**
     * Create a new apartment.
     * @param apartment DTO containing apartment details.
     * @return Created apartment details.
     */
    @Operation(summary = "Create a new apartment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the apartment",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApartmentResponseDTO.class)) })
    })
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApartmentResponseDTO> createApartment(@RequestBody @Valid ApartmentRequestDTONoId apartment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(apartmentService.save(apartment));
    }

    /**
     * Update an existing apartment.
     * @param apartment DTO containing updated apartment details.
     * @return Updated apartment details.
     */
    @Operation(summary = "Update an existing apartment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the apartment",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApartmentResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Apartment not found",
                    content = @Content)
    })
    @RequestMapping(
            path = "/updateApartments/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApartmentResponseDTO> updateApartment(@PathVariable Integer id,@RequestBody @Valid ApartmentRequestDTONoId apartment) {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.update(id,apartment));
    }

    /**
     * Delete an apartment by its ID.
     * @param id ID of the apartment to delete.
     * @return String.
     */
    @Operation(summary = "Delete an apartment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted the apartment",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Apartment not found",
                    content = @Content)
    })
    @RequestMapping(
            path = "/deleteApartmentsById/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteApartment(@PathVariable Integer id) {
        apartmentService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Retrieve the event associated with an apartment by apartment ID.
     * @param id ID of the apartment.
     * @return Event details.
     */
    @Operation(summary = "Retrieve the event associated with an apartment by apartment ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the event",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content)
    })
    @GetMapping("apartment/{id}/event")
    public ResponseEntity<EventResponseDTO> getEventByApartmentId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.getEventByApartmentId(id));
    }

    /**
     * Retrieve the photos associated with an apartment by apartment ID.
     * @param id ID of the apartment.
     * @return List of photos.
     */
    @Operation(summary = "Retrieve the photos associated with an apartment by apartment ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the photos",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PhotoResponseDTONoIdNoFk.class)) })
    })
    @RequestMapping(
            path = "/apartment/{id}/event",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PhotoResponseDTONoIdNoFk>>getPhotosByApartmentId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.getPhotosByApartmentId(id));
    }

    /**
     * Retrieve the reservations associated with an apartment by apartment ID.
     * @param id ID of the apartment.
     * @return List of reservations.
     */
    @Operation(summary = "Retrieve the reservations associated with an apartment by apartment ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the reservations",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationResponseDTONoIdNoFk.class)) })
    })
    @RequestMapping(
            path = "/apartments/{id}/reservations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReservationResponseDTONoIdNoFk>> getReservationsByApartmentId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.getReservationsByApartmentId(id));
    }

}
