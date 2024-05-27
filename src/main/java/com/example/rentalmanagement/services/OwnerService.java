package com.example.rentalmanagement.services;

import com.example.rentalmanagement.exceptions.ResourceNotFoundException;
import com.example.rentalmanagement.models.DTO.apartment.ApartmentResponseDTONoIdNoFk;
import com.example.rentalmanagement.models.DTO.owner.OwnerRequestDTONoId;
import com.example.rentalmanagement.models.DTO.owner.OwnerResponseDTO;
import com.example.rentalmanagement.models.entities.ApartmentEnt;
import com.example.rentalmanagement.models.entities.OwnerEnt;
import com.example.rentalmanagement.repository.ApartmentRepository;
import com.example.rentalmanagement.repository.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final ApartmentRepository apartmentRepository;

    public OwnerService(OwnerRepository ownerRepository, ApartmentRepository apartmentRepository) {
        this.ownerRepository = ownerRepository;
        this.apartmentRepository = apartmentRepository;
    }


    public List<OwnerResponseDTO> findAll() {
        log.info("Retrieving all owners");
        List<OwnerEnt> owners = ownerRepository.findAll();
        return owners.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }


    public OwnerResponseDTO findById(Integer id) {
        log.info("Retrieving owner with ID: {}", id);
        OwnerEnt owner = ownerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Owner not found with ID: {}", id);
                    return new ResourceNotFoundException("Owner", "id", id);
                });
        return convertToResponseDTO(owner);
    }


    public OwnerResponseDTO save(OwnerRequestDTONoId ownerDTO) {
        log.info("Creating new owner with email: {}", ownerDTO.getEmail());
        OwnerEnt owner = new OwnerEnt();
        BeanUtils.copyProperties(ownerDTO, owner);
        owner = ownerRepository.save(owner);
        return convertToResponseDTO(owner);
    }


    public OwnerResponseDTO update(Integer id, OwnerRequestDTONoId ownerDTO) {
        log.info("Updating owner with ID: {}", id);
        OwnerEnt existingOwner = ownerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Owner not found with ID: {}", id);
                    return new ResourceNotFoundException("Owner", "id", id);
                });
        BeanUtils.copyProperties(ownerDTO, existingOwner, "id");
        ownerRepository.save(existingOwner);
        return convertToResponseDTO(existingOwner);
    }


    public void delete(Integer id) {
        log.info("Deleting owner with ID: {}", id);
        if (!ownerRepository.existsById(id)) {
            log.error("Owner not found with ID: {}", id);
            throw new ResourceNotFoundException("Owner", "id", id);
        }
        ownerRepository.deleteById(id);
    }


    public List<ApartmentResponseDTONoIdNoFk> getApartmentsByOwnerId(Integer id) {
        log.info("Retrieving apartments for owner with ID: {}", id);
        if (!ownerRepository.existsById(id)) {
            log.error("Owner not found with ID: {}", id);
            throw new ResourceNotFoundException("Owner", "id", id);
        }
        List<ApartmentEnt> apartments = apartmentRepository.findByOwnerId(id);
        return apartments.stream()
                .map(this::convertToApartmentResponseDTO)
                .collect(Collectors.toList());
    }

    private OwnerResponseDTO convertToResponseDTO(OwnerEnt owner) {
        OwnerResponseDTO dto = new OwnerResponseDTO();
        BeanUtils.copyProperties(owner, dto);
        return dto;
    }

    private ApartmentResponseDTONoIdNoFk convertToApartmentResponseDTO(ApartmentEnt apartment) {
        ApartmentResponseDTONoIdNoFk dto = new ApartmentResponseDTONoIdNoFk();
        BeanUtils.copyProperties(apartment, dto);
        return dto;
    }
}