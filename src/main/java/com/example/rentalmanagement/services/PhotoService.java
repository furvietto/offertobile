package com.example.rentalmanagement.services;

import com.example.rentalmanagement.exceptions.ResourceNotFoundException;
import com.example.rentalmanagement.models.DTO.photo.PhotoRequestDTONoId;
import com.example.rentalmanagement.models.DTO.photo.PhotoResponseDTO;
import com.example.rentalmanagement.models.entities.ApartmentEnt;
import com.example.rentalmanagement.models.entities.PhotoEnt;
import com.example.rentalmanagement.repository.ApartmentRepository;
import com.example.rentalmanagement.repository.PhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final ApartmentRepository apartmentRepository;

    public PhotoService(PhotoRepository photoRepository, ApartmentRepository apartmentRepository) {
        this.photoRepository = photoRepository;
        this.apartmentRepository = apartmentRepository;
    }


    public List<PhotoResponseDTO> findAll() {
        log.info("Retrieving all photos");
        List<PhotoEnt> photos = photoRepository.findAll();
        return photos.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public PhotoResponseDTO findById(int id) {
        log.info("Retrieving photo with ID: {}", id);
        PhotoEnt photo = photoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Photo not found with ID: {}", id);
                    return new ResourceNotFoundException("Photo", "id", id);
                });
        return convertToResponseDTO(photo);
    }

    public PhotoResponseDTO save(PhotoRequestDTONoId photoDTO) {
        log.info("Creating new photo");
        PhotoEnt photo = new PhotoEnt();
        BeanUtils.copyProperties(photoDTO, photo);
        setReferences(photo, photoDTO);
        photo = photoRepository.save(photo);
        return convertToResponseDTO(photo);
    }

    public PhotoResponseDTO update(int id, PhotoRequestDTONoId photoDTO) {
        log.info("Updating photo with ID: {}", id);
        PhotoEnt existingPhoto = photoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Photo not found with ID: {}", id);
                    return new ResourceNotFoundException("Photo", "id", id);
                });
        BeanUtils.copyProperties(photoDTO, existingPhoto, "id");
        setReferences(existingPhoto, photoDTO);
        photoRepository.save(existingPhoto);
        return convertToResponseDTO(existingPhoto);
    }

    public void delete(int id) {
        log.info("Deleting photo with ID: {}", id);
        if (!photoRepository.existsById(id)) {
            log.error("Photo not found with ID: {}", id);
            throw new ResourceNotFoundException("Photo", "id", id);
        }
        photoRepository.deleteById(id);
    }

    private void setReferences(PhotoEnt photo, PhotoRequestDTONoId photoDTO) {
        ApartmentEnt apartment = apartmentRepository.findById(photoDTO.getApartmentId())
                .orElseThrow(() -> {
                    log.error("Apartment not found with ID: {}", photoDTO.getApartmentId());
                    return new ResourceNotFoundException("Apartment", "id", photoDTO.getApartmentId());
                });
        photo.setApartment(apartment);
    }

    private PhotoResponseDTO convertToResponseDTO(PhotoEnt photo) {
        PhotoResponseDTO dto = new PhotoResponseDTO();
        BeanUtils.copyProperties(photo, dto);
        dto.setApartmentId(photo.getApartment().getId());
        return dto;
    }
}
