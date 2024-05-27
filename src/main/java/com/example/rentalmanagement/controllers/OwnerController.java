package com.example.rentalmanagement.controllers;

import com.example.rentalmanagement.models.DTO.apartment.ApartmentResponseDTONoIdNoFk;
import com.example.rentalmanagement.models.DTO.owner.OwnerRequestDTONoId;
import com.example.rentalmanagement.models.DTO.owner.OwnerResponseDTO;
import com.example.rentalmanagement.services.OwnerService;
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
@RequestMapping("/v1/owners")
@Tag(name = "Owner Management", description = "Operations pertaining to owners in the Rental Management System")
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
    @Operation(summary = "Retrieve all owners")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the owners",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OwnerResponseDTO.class)) })
    })
    @RequestMapping(
            method = RequestMethod.GET,
            path ="/getAllOwners",
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
    @Operation(summary = "Retrieve an owner by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the owner",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OwnerResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Owner not found",
                    content = @Content)
    })
    @RequestMapping(
            method = RequestMethod.GET,
            path ="/getOwnerById/{id}",
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
    @Operation(summary = "Retrieve apartments by owner ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the apartments",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApartmentResponseDTONoIdNoFk.class)) }),
            @ApiResponse(responseCode = "404", description = "Owner not found",
                    content = @Content)
    })
    @RequestMapping(
            path = "/owners/{id}/apartments",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ApartmentResponseDTONoIdNoFk>> getApartmentsByOwnerId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(ownerService.getApartmentsByOwnerId(id));
    }


    /**
     * Create a new owner.
     * @param owner DTO containing owner details.
     * @return Created owner details.
     */
    @Operation(summary = "Create a new owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the owner",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OwnerResponseDTO.class)) })
    })
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
    @Operation(summary = "Update an existing owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the owner",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OwnerResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Owner not found",
                    content = @Content)
    })
    @RequestMapping(
            method = RequestMethod.PUT,
            path ="/updateOwner/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OwnerResponseDTO> updateOwner(@PathVariable Integer id, @RequestBody @Valid OwnerRequestDTONoId owner) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerService.update(id,owner));
    }

    /**
     * Delete an owner by its ID.
     * @param id ID of the owner to delete.
     * @return String.
     */
    @Operation(summary = "Delete an owner by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted the owner",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Owner not found",
                    content = @Content)
    })
    @RequestMapping(
            method = RequestMethod.DELETE,
            path ="/deleteOwnersById/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deleteOwner(@PathVariable Integer id) {
        ownerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
