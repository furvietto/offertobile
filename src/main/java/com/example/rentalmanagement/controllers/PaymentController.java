package com.example.rentalmanagement.controllers;

import com.example.rentalmanagement.models.DTO.payment.PaymentRequestDTONoId;
import com.example.rentalmanagement.models.DTO.payment.PaymentResponseDTO;
import com.example.rentalmanagement.models.DTO.payment.PaymentResponseDTONoIdNoFk;
import com.example.rentalmanagement.services.PaymentService;
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
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Retrieve all payments.
     * @return List of all payments.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            path ="/getAllPayments",
            consumes = MediaType.APPLICATION_JSON_VALUE,
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
    @RequestMapping(
            method = RequestMethod.GET,
            path ="/getPaymentById/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PaymentResponseDTO> getPayment(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body( paymentService.findById(id));
    }

    /**
     * Create a new payment.
     * @param payment DTO containing payment details.
     * @return Created payment details.
     */
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
    @RequestMapping(
            method = RequestMethod.PUT,
            path ="/updatePayment/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PaymentResponseDTO> updatePayment(@PathVariable int id, @RequestBody @Valid PaymentRequestDTONoId payment) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.update(id, payment));
    }

    /**
     * Delete a payment by its ID.
     * @param id ID of the payment to delete.
     * @return No content.
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            path ="/deletePaymentById/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deletePayment(@PathVariable int id) {
        paymentService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Retrieve the reservation associated with a payment by payment ID.
     * @param id ID of the payment.
     * @return Reservation details associated with the payment.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            path ="/payment/{id}/reservation",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PaymentResponseDTONoIdNoFk> getReservationByPaymentId(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getReservationByPaymentId(id));
    }
}
