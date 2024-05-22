package com.example.rentalmanagement.controllers;

import com.example.rentalmanagement.models.DTO.payment.PaymentResponseDTONoIdNoFk;
import com.example.rentalmanagement.models.DTO.reservation.ReservationRequestDTONoId;
import com.example.rentalmanagement.models.DTO.reservation.ReservationResponseDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/v1/reservations")
public class ReservationController {

    /**
     * Retrieve all reservations.
     * @return List of all reservations.
     */
    @RequestMapping(
            path = "/getAllReservation",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.findAll());
    }

    /**
     * Retrieve a reservation by its ID.
     * @param id ID of the reservation.
     * @return Reservation details.
     */
    @RequestMapping(
            path = "/getReservationById",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationResponseDTO> getReservation(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.findById(id));
    }

    /**
     * Create a new reservation.
     * @param reservation DTO containing reservation details.
     * @return Created reservation details.
     */
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
    @RequestMapping(
            method = RequestMethod.PUT,
            path = "/updateReservation/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationResponseDTO> updateReservation(@PathVariable int id, @RequestBody @Valid ReservationRequestDTONoId reservation) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.update(id, reservation));
    }

    /**
     * Delete a reservation by its ID.
     * @param id ID of the reservation to delete.
     * @return No content.
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/deleteReservation/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteReservation(@PathVariable int id) {
        reservationService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Retrieve payments by reservation ID.
     * @param id ID of the reservation.
     * @return List of payments associated with the reservation.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            path = "/reservation/{id}/payments",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PaymentResponseDTONoIdNoFk>> getPaymentsByReservationId(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getPaymentsByReservationId(id));
    }
}
