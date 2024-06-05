package com.example.rentalmanagement.controllers;

import com.example.rentalmanagement.models.DTO.payment.PaymentResponseDTONoIdNoFk;
import com.example.rentalmanagement.models.DTO.reservation.ReservationRequestDTONoId;
import com.example.rentalmanagement.models.DTO.reservation.ReservationResponseDTO;
import com.example.rentalmanagement.services.PhotoService;
import com.example.rentalmanagement.services.ReservationService;
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

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/v1/reservations")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Reservation Management", description = "Operations pertaining to reservations in the Rental Management System")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Retrieve all reservations.
     * @return List of all reservations.
     */
    @Operation(summary = "Retrieve all reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the reservations",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationResponseDTO.class)) })
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'OWNER')")
    @RequestMapping(
            path = "/getAllReservation",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.findAll());
    }

    /**
     * Retrieve a reservation by its ID.
     * @param id ID of the reservation.
     * @return Reservation details.
     */
    @Operation(summary = "Retrieve a reservation by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the reservation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Reservation not found",
                    content = @Content)
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'OWNER')")
    @RequestMapping(
            path = "/getReservationById/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationResponseDTO> getReservation(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.findById(id));
    }

    /**
     * Create a new reservation.
     * @param reservation DTO containing reservation details.
     * @return Created reservation details.
     */
    @Operation(summary = "Create a new reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the reservation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationResponseDTO.class)) })
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody @Valid ReservationRequestDTONoId reservation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.save(reservation));
    }

    /**
     * Update an existing reservation.
     * @param id ID of the reservation to update.
     * @param reservation DTO containing updated reservation details.
     * @return Updated reservation details.
     */
    @Operation(summary = "Update an existing reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the reservation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Reservation not found",
                    content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @RequestMapping(
            method = RequestMethod.PUT,
            path = "/updateReservation/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationResponseDTO> updateReservation(@PathVariable Integer id, @RequestBody @Valid ReservationRequestDTONoId reservation) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.update(id, reservation));
    }

    /**
     * Delete a reservation by its ID.
     * @param id ID of the reservation to delete.
     * @return No content.
     */
    @Operation(summary = "Delete a reservation by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted the reservation",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Reservation not found",
                    content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/deleteReservation/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer id) {
        reservationService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Retrieve payments by reservation ID.
     * @param id ID of the reservation.
     * @return List of payments associated with the reservation.
     */
    @Operation(summary = "Retrieve payments by reservation ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the payments",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentResponseDTONoIdNoFk.class)) }),
            @ApiResponse(responseCode = "404", description = "Reservation not found",
                    content = @Content)
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'OWNER')")
    @RequestMapping(
            method = RequestMethod.GET,
            path = "/reservation/{id}/payments",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PaymentResponseDTONoIdNoFk>> getPaymentsByReservationId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getPaymentsByReservationId(id));
    }
}
