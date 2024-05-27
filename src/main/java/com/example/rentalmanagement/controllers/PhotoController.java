package com.example.rentalmanagement.controllers;

import com.example.rentalmanagement.models.DTO.photo.PhotoRequestDTONoId;
import com.example.rentalmanagement.models.DTO.photo.PhotoResponseDTO;
import com.example.rentalmanagement.services.PhotoService;
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
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    /**
     * Retrieve all photos.
     * @return List of all photos.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            path ="/getAllphotos",
            consumes = MediaType.APPLICATION_JSON_VALUE,
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
    @RequestMapping(
            method = RequestMethod.GET,
            path ="/getPhotoById/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
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
    @RequestMapping(
            method = RequestMethod.DELETE,
            path ="/deletePaymentById/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deletePhoto(@PathVariable Integer id) {
        photoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
