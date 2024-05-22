package com.example.rentalmanagement.controllers;

import com.example.rentalmanagement.models.DTO.apartment.ApartmentResponseDTONoIdNoFk;
import com.example.rentalmanagement.models.DTO.owner.OwnerRequestDTONoId;
import com.example.rentalmanagement.models.DTO.owner.OwnerResponseDTO;
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
@RequestMapping("/v1/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService){
        this.ownerService = ownerService;
    };

    // Owner APIs
    /**
     * Retrieve all owners.
     * @return List of all owners.
     */

    @RequestMapping(
            method = RequestMethod.GET,
            path ="/getAllOwners",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<OwnerResponseDTO>> getAllOwners() {
        return ResponseEntity.status(HttpStatus.OK).body(ownerService.findAll());
    }

    /**
     * Retrieve an owner by its ID.
     * @param id ID of the owner.
     * @return Owner details.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            path ="/getOwnerById/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OwnerResponseDTO> getOwner(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body( ownerService.findById(id));
    }


    /**
     * Retrieve apartments by owner ID.
     * @param id ID of the owner.
     * @return List of apartments.
     */
    @RequestMapping(
            path = "/owners/{id}/apartments",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ApartmentResponseDTONoIdNoFk>> getApartmentsByOwnerId(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(ownerService.getApartmentsByOwnerId(id));
    }


    /**
     * Create a new owner.
     * @param owner DTO containing owner details.
     * @return Created owner details.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerResponseDTO> createOwner(@RequestBody @Valid OwnerRequestDTONoId owner) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerService.save(owner));
    }

    /**
     * Update an existing owner.
     * @param id ID of the owner to update.
     * @param owner DTO containing updated owner details.
     * @return Updated owner details.
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            path ="/updateOwner/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OwnerResponseDTO> updateOwner(@PathVariable int id, @RequestBody @Valid OwnerRequestDTONoId owner) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerService.update(id,owner));
    }

    /**
     * Delete an owner by its ID.
     * @param id ID of the owner to delete.
     * @return String.
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            path ="/deleteOwnersById/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deleteOwner(@PathVariable Integer id) {
        ownerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
