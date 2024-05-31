package com.example.rentalmanagement.services;

import com.example.rentalmanagement.exceptions.ResourceNotFoundException;
import com.example.rentalmanagement.models.DTO.apartment.ApartmentRequestDTONoId;
import com.example.rentalmanagement.models.DTO.apartment.ApartmentResponseDTO;
import com.example.rentalmanagement.models.DTO.apartment.ApartmentResponseDTONoIdNoFk;
import com.example.rentalmanagement.models.DTO.event.EventResponseDTO;
import com.example.rentalmanagement.models.DTO.photo.PhotoResponseDTONoIdNoFk;
import com.example.rentalmanagement.models.DTO.reservation.ReservationResponseDTONoIdNoFk;
import com.example.rentalmanagement.models.entities.*;
import com.example.rentalmanagement.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final EventRepository eventRepository;
    private final PhotoRepository photoRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public ApartmentService(ApartmentRepository apartmentRepository, EventRepository eventRepository, PhotoRepository photoRepository, ReservationRepository reservationRepository, UserRepository userRepository) {
        this.apartmentRepository = apartmentRepository;
        this.eventRepository = eventRepository;
        this.photoRepository = photoRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    public List<ApartmentResponseDTO> findAll() {
        log.info("Retrieving all apartments");
        List<ApartmentEnt> apartments = apartmentRepository.findAll();
        return apartments.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public ApartmentResponseDTO findById(Integer id) {
        log.info("Retrieving apartment with ID: {}", id);
        ApartmentEnt apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Apartment not found with ID: {}", id);
                    return new ResourceNotFoundException("Apartment", "id", id);
                });
        return convertToResponseDTO(apartment);
    }

    public ApartmentResponseDTO save(ApartmentRequestDTONoId apartmentDTO) {
        log.info("Creating new apartment with address: {}", apartmentDTO.getAddress());
        ApartmentEnt apartment = new ApartmentEnt();
        BeanUtils.copyProperties(apartmentDTO, apartment);
        setReferences(apartment, apartmentDTO);
        apartment = apartmentRepository.save(apartment);
        return convertToResponseDTO(apartment);
    }

    public ApartmentResponseDTO update(Integer id, ApartmentRequestDTONoId apartmentDTO) {
        log.info("Updating apartment with ID: {}", id);
        ApartmentEnt existingApartment = apartmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Apartment not found with ID: {}", id);
                    return new ResourceNotFoundException("Apartment", "id", id);
                });
        BeanUtils.copyProperties(apartmentDTO, existingApartment, "id");
        setReferences(existingApartment, apartmentDTO);
        apartmentRepository.save(existingApartment);
        return convertToResponseDTO(existingApartment);
    }

    public void delete(Integer id) {
        log.info("Deleting apartment with ID: {}", id);
        if (!apartmentRepository.existsById(id)) {
            log.error("Apartment not found with ID: {}", id);
            throw new ResourceNotFoundException("Apartment", "id", id);
        }
        apartmentRepository.deleteById(id);
    }

    public EventResponseDTO getEventByApartmentId(Integer id) {
        log.info("Retrieving event for apartment with ID: {}", id);
        ApartmentEnt apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Apartment not found with ID: {}", id);
                    return new ResourceNotFoundException("Apartment", "id", id);
                });
        EventEnt event = apartment.getEvent();
        return convertToEventResponseDTO(event);
    }

    public List<PhotoResponseDTONoIdNoFk> getPhotosByApartmentId(Integer id) {
        log.info("Retrieving photos for apartment with ID: {}", id);
        if (!apartmentRepository.existsById(id)) {
            log.error("Apartment not found with ID: {}", id);
            throw new ResourceNotFoundException("Apartment", "id", id);
        }
        List<PhotoEnt> photos = photoRepository.findByApartmentId(id);
        return photos.stream()
                .map(this::convertToPhotoResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationResponseDTONoIdNoFk> getReservationsByApartmentId(Integer id) {
        log.info("Retrieving reservations for apartment with ID: {}", id);
        if (!apartmentRepository.existsById(id)) {
            log.error("Apartment not found with ID: {}", id);
            throw new ResourceNotFoundException("Apartment", "id", id);
        }
        List<ReservationEnt> reservations = reservationRepository.findByApartmentId(id);
        return reservations.stream()
                .map(this::convertToReservationResponseDTO)
                .collect(Collectors.toList());
    }

    private void setReferences(ApartmentEnt apartment, ApartmentRequestDTONoId apartmentDTO) {
        UserEnt owner = userRepository.findById(apartmentDTO.getOwnerId())
                .orElseThrow(() -> {
                    log.error("Owner not found with ID: {}", apartmentDTO.getOwnerId());
                    return new ResourceNotFoundException("Owner", "id", apartmentDTO.getOwnerId());
                });
        EventEnt event = eventRepository.findById(apartmentDTO.getEventId())
                .orElseThrow(() -> {
                    log.error("Event not found with ID: {}", apartmentDTO.getEventId());
                    return new ResourceNotFoundException("Event", "id", apartmentDTO.getEventId());
                });
        apartment.setUser(owner);
        apartment.setEvent(event);
    }

    private ApartmentResponseDTO convertToResponseDTO(ApartmentEnt apartment) {
        ApartmentResponseDTO dto = new ApartmentResponseDTO();
        BeanUtils.copyProperties(apartment, dto);
        dto.setOwnerId(apartment.getUser().getId());
        dto.setEventId(apartment.getEvent().getId());
        return dto;
    }

    private EventResponseDTO convertToEventResponseDTO(EventEnt event) {
        EventResponseDTO dto = new EventResponseDTO();
        BeanUtils.copyProperties(event, dto);
        return dto;
    }

    private PhotoResponseDTONoIdNoFk convertToPhotoResponseDTO(PhotoEnt photo) {
        PhotoResponseDTONoIdNoFk dto = new PhotoResponseDTONoIdNoFk();
        BeanUtils.copyProperties(photo, dto);
        return dto;
    }

    private ReservationResponseDTONoIdNoFk convertToReservationResponseDTO(ReservationEnt reservation) {
        ReservationResponseDTONoIdNoFk dto = new ReservationResponseDTONoIdNoFk();
        BeanUtils.copyProperties(reservation, dto);
        return dto;
    }
}
