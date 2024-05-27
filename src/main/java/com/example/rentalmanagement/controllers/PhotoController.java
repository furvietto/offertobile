package com.example.rentalmanagement.controllers;

import com.example.rentalmanagement.models.DTO.photo.PhotoRequestDTONoId;
import com.example.rentalmanagement.models.DTO.photo.PhotoResponseDTO;
import com.example.rentalmanagement.services.PhotoService;
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
@RequestMapping("/v1/photos")
@Tag(name = "Photo Management", description = "Operations pertaining to photos in the Rental Management System")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    /**
     * Retrieve all photos.
     * @return List of all photos.
     */
    @Operation(summary = "Retrieve all photos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the photos",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PhotoResponseDTO.class)) })
    })
    @RequestMapping(
            method = RequestMethod.GET,
            path ="/getAllphotos",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<PhotoResponseDTO>> getAllPhotos() {
        return ResponseEntity.status(HttpStatus.OK).body(photoService.findAll());
    }

    /**
     * Retrieve a photo by its ID.
     * @param id ID of the photo.
     * @return Photo details.
     */
    @Operation(summary = "Retrieve a photo by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the photo",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PhotoResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Photo not found",
                    content = @Content)
    })
    @RequestMapping(
            method = RequestMethod.GET,
            path ="/getPhotoById/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PhotoResponseDTO> getPhoto(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(photoService.findById(id));
    }

    /**
     * Create a new photo.
     * @param photo DTO containing photo details.
     * @return Created photo details.
     */
    @Operation(summary = "Create a new photo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the photo",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PhotoResponseDTO.class)) })
    })
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PhotoResponseDTO> createPhoto(@RequestBody @Valid PhotoRequestDTONoId photo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(photoService.save(photo));
    }

    /**
     * Update an existing photo.
     * @param id ID of the photo to update.
     * @param photo DTO containing updated photo details.
     * @return Updated photo details.
     */
    @Operation(summary = "Update an existing photo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the photo",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PhotoResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Photo not found",
                    content = @Content)
    })
    @RequestMapping(
            method = RequestMethod.PUT,
            path ="/updatePhoto/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PhotoResponseDTO> updatePhoto(@PathVariable Integer id, @RequestBody @Valid PhotoRequestDTONoId photo) {
        return ResponseEntity.status(HttpStatus.OK).body(photoService.update(id, photo));
    }

    /**
     * Delete a photo by its ID.
     * @param id ID of the photo to delete.
     * @return No content.
     */
    @Operation(summary = "Delete a photo by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted the photo",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Photo not found",
                    content = @Content)
    })
    @RequestMapping(
            method = RequestMethod.DELETE,
            path ="/deletePaymentById/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deletePhoto(@PathVariable Integer id) {
        photoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
