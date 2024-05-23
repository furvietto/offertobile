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
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/v1/apartments")
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
    @RequestMapping(
            path = "/getAllApartments",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ApartmentResponseDTO>> getAllApartments() {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.findAll());
    }

    /**
     * Retrieve an apartment by its ID.
     * @param id ID of the apartment.
     * @return Apartment details.
     */
    @RequestMapping(
            path = "/getApartmentById/{id}",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApartmentResponseDTO> getApartment(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.findById(id));
    }

    /**
     * Create a new apartment.
     * @param apartment DTO containing apartment details.
     * @return Created apartment details.
     */
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
    @RequestMapping(
            path = "/deleteApartmentsById/{id}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteApartment(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Retrieve the event associated with an apartment by apartment ID.
     * @param id ID of the apartment.
     * @return Event details.
     */
    @GetMapping("apartment/{id}/event")
    public ResponseEntity<EventResponseDTO> getEventByApartmentId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.getEventByApartmentId(id));
    }

    /**
     * Retrieve the photos associated with an apartment by apartment ID.
     * @param id ID of the apartment.
     * @return List of photos.
     */
    @RequestMapping(
            path = "/apartments/{id}/photos",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PhotoResponseDTONoIdNoFk>>getPhotosByApartmentId(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.getPhotosByApartmentId(id));
    }

    /**
     * Retrieve the reservations associated with an apartment by apartment ID.
     * @param id ID of the apartment.
     * @return List of reservations.
     */
    @RequestMapping(
            path = "/apartments/{id}/reservations",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReservationResponseDTONoIdNoFk>> getReservationsByApartmentId(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.getReservationsByApartmentId(id));
    }

    /*
    //we manage the specific exception here and return the custom message to client
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException (ResourceNotFoundException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "APARTMENT_NOT_FOUND"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
     */
}
