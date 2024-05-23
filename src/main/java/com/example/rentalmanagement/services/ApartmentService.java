package com.example.rentalmanagement.services;

import com.example.rentalmanagement.exceptions.ResourceNotFoundException;
import com.example.rentalmanagement.models.DTO.apartment.ApartmentRequestDTONoId;
import com.example.rentalmanagement.models.DTO.apartment.ApartmentResponseDTO;
import com.example.rentalmanagement.models.DTO.event.EventResponseDTO;
import com.example.rentalmanagement.models.DTO.photo.PhotoResponseDTONoIdNoFk;
import com.example.rentalmanagement.models.DTO.reservation.ReservationResponseDTONoIdNoFk;
import com.example.rentalmanagement.models.entities.ApartmentEnt;
import com.example.rentalmanagement.models.entities.EventEnt;
import com.example.rentalmanagement.models.entities.PhotoEnt;
import com.example.rentalmanagement.models.entities.ReservationEnt;
import com.example.rentalmanagement.repository.ApartmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final EventRepository eventRepository;

    private final OwnerRepository ownerRepository;
    private final PhotoRepository photoRepository;
    private final ReservationRepository reservationRepository;

    public ApartmentService(ApartmentRepository apartmentRepository,OwnerRepository ownerRepository ,EventRepository eventRepository,
                            PhotoRepository photoRepository, ReservationRepository reservationRepository) {
        this.apartmentRepository = apartmentRepository;
        this.ownerRepository = ownerRepository;
        this.eventRepository = eventRepository;
        this.photoRepository = photoRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<ApartmentResponseDTO> findAll() {
        return apartmentRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public ApartmentResponseDTO findById(Integer id) {
        return apartmentRepository.findById(id)
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Apartment","id",id));
    }

    public ApartmentResponseDTO save(ApartmentRequestDTONoId apartmentRequestDTONoId) {
        ApartmentEnt apartment = new ApartmentEnt();
        BeanUtils.copyProperties(apartmentRequestDTONoId, apartment);

        // Set owner and event
        apartment.setOwner(ownerRepository.findById(apartmentRequestDTONoId.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner","id",apartmentRequestDTONoId.getOwnerId())));
        apartment.setEvent(eventRepository.findById(apartmentRequestDTONoId.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event","id",apartmentRequestDTONoId.getEventId())));

        ApartmentEnt savedApartment = apartmentRepository.save(apartment);
        return convertToResponseDTO(savedApartment);
    }

    public ApartmentResponseDTO update(int id, ApartmentRequestDTONoId apartmentRequestDTO) {
        ApartmentEnt apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Apartment","id",id));

        // Copy properties from DTO to entity
        BeanUtils.copyProperties(apartmentRequestDTO, apartment);

        // Update owner and event
        apartment.setOwner(ownerRepository.findById(apartmentRequestDTO.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner","id",id)));
        apartment.setEvent(eventRepository.findById(apartmentRequestDTO.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event","id",id)));

        ApartmentEnt updatedApartment = apartmentRepository.save(apartment);
        return convertToResponseDTO(updatedApartment);
    }

    public void delete(Integer id) {
        ApartmentEnt apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Apartment","id",id));
        apartmentRepository.delete(apartment);
    }

    public EventResponseDTO getEventByApartmentId(Integer apartmentId) {
        ApartmentEnt apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Apartment","id",apartmentId));
        EventEnt event = apartment.getEvent();
        return new EventResponseDTO(event.getId(), event.getEventName(), event.getDescription(),
                event.getStartDate(), event.getEndDate(), event.getLocation(), event.getEventType());
    }

    public List<PhotoResponseDTONoIdNoFk> getPhotosByApartmentId(Integer apartmentId) {
        ApartmentEnt apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Apartment","id",apartmentId));
        List<PhotoEnt> photos = apartment.getPhotos();
        return photos.stream()
                .map(photo -> new PhotoResponseDTONoIdNoFk(photo.getPhoto(), photo.getDescription()))
                .collect(Collectors.toList());
    }

    public List<ReservationResponseDTONoIdNoFk> getReservationsByApartmentId(Integer apartmentId) {
        ApartmentEnt apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Apartment","id",apartmentId));
        List<ReservationEnt> reservations = apartment.getReservations();
        return reservations.stream()
                .map(reservation -> new ReservationResponseDTONoIdNoFk(
                        reservation.getCustomer(),
                        reservation.getStartDate(),
                        reservation.getEndDate(),
                        reservation.getTotalPayment(),
                        reservation.getStatus()))
                .collect(Collectors.toList());
    }

    private ApartmentResponseDTO convertToResponseDTO(ApartmentEnt apartment) {
        ApartmentResponseDTO responseDTO = new ApartmentResponseDTO();
        BeanUtils.copyProperties(apartment, responseDTO);
        responseDTO.setOwnerId(apartment.getOwner().getId());
        responseDTO.setEventId(apartment.getEvent().getId());
        return responseDTO;
    }
}
