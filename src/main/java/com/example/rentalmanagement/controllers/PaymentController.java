package com.example.rentalmanagement.controllers;

import com.example.rentalmanagement.models.DTO.payment.PaymentRequestDTONoId;
import com.example.rentalmanagement.models.DTO.payment.PaymentResponseDTO;
import com.example.rentalmanagement.models.DTO.payment.PaymentResponseDTONoIdNoFk;
import com.example.rentalmanagement.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/v1/payments")
@Tag(name = "Payment Management", description = "Operations pertaining to payments in the Rental Management System")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Retrieve all payments.
     * @return List of all payments.
     */
    @Operation(summary = "Retrieve all payments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the payments",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentResponseDTO.class)) })
    })
    @RequestMapping(
            method = RequestMethod.GET,
            path ="/getAllPayments",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments() {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.findAll());
    }

    /**
     * Retrieve a payment by its ID.
     * @param id ID of the payment.
     * @return Payment details.
     */
    @Operation(summary = "Retrieve a payment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the payment",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Payment not found",
                    content = @Content)
    })
    @RequestMapping(
            method = RequestMethod.GET,
            path ="/getPaymentById/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PaymentResponseDTO> getPayment(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body( paymentService.findById(id));
    }

    /**
     * Create a new payment.
     * @param payment DTO containing payment details.
     * @return Created payment details.
     */
    @Operation(summary = "Create a new payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the payment",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentResponseDTO.class)) })
    })
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentResponseDTO> createPayment(@RequestBody @Valid PaymentRequestDTONoId payment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.save(payment));
    }

    /**
     * Update an existing payment.
     * @param id ID of the payment to update.
     * @param payment DTO containing updated payment details.
     * @return Updated payment details.
     */
    @Operation(summary = "Update an existing payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the payment",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Payment not found",
                    content = @Content)
    })
    @RequestMapping(
            method = RequestMethod.PUT,
            path ="/updatePayment/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PaymentResponseDTO> updatePayment(@PathVariable Integer id, @RequestBody @Valid PaymentRequestDTONoId payment) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.update(id, payment));
    }

    /**
     * Delete a payment by its ID.
     * @param id ID of the payment to delete.
     * @return No content.
     */
    @Operation(summary = "Delete a payment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted the payment",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Payment not found",
                    content = @Content)
    })
    @RequestMapping(
            method = RequestMethod.DELETE,
            path ="/deletePaymentById/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deletePayment(@PathVariable Integer id) {
        paymentService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Retrieve the reservation associated with a payment by payment ID.
     * @param id ID of the payment.
     * @return Reservation details associated with the payment.
     */
    @Operation(summary = "Retrieve the reservation associated with a payment by payment ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the reservation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentResponseDTONoIdNoFk.class)) }),
            @ApiResponse(responseCode = "404", description = "Reservation not found",
                    content = @Content)
    })
    @RequestMapping(
            method = RequestMethod.GET,
            path ="/payment/{id}/reservation",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PaymentResponseDTONoIdNoFk> getReservationByPaymentId(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getReservationByPaymentId(id));
    }
}
