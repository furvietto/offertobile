package com.example.rentalmanagement.services;

import com.example.rentalmanagement.exceptions.ResourceNotFoundException;
import com.example.rentalmanagement.models.DTO.apartment.ApartmentResponseDTONoIdNoFk;
import com.example.rentalmanagement.models.DTO.event.EventRequestDTONoId;
import com.example.rentalmanagement.models.DTO.event.EventResponseDTO;
import com.example.rentalmanagement.models.entities.ApartmentEnt;
import com.example.rentalmanagement.models.entities.EventEnt;
import com.example.rentalmanagement.repository.ApartmentRepository;
import com.example.rentalmanagement.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final ApartmentRepository apartmentRepository;

    public EventService(EventRepository eventRepository, ApartmentRepository apartmentRepository) {
        this.eventRepository = eventRepository;
        this.apartmentRepository = apartmentRepository;
    }

    public List<EventResponseDTO> findAll() {
        log.info("Retrieving all events");
        List<EventEnt> events = eventRepository.findAll();
        return events.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public EventResponseDTO findById(int id) {
        log.info("Retrieving event with ID: {}", id);
        EventEnt event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Event not found with ID: {}", id);
                    return new ResourceNotFoundException("Event", "id", id);
                });
        return convertToResponseDTO(event);
    }


    public EventResponseDTO save(EventRequestDTONoId eventDTO) {
        log.info("Creating new event with name: {}", eventDTO.getEventName());
        EventEnt event = new EventEnt();
        BeanUtils.copyProperties(eventDTO, event);
        event = eventRepository.save(event);
        return convertToResponseDTO(event);
    }


    public EventResponseDTO update(int id, EventRequestDTONoId eventDTO) {
        log.info("Updating event with ID: {}", id);
        EventEnt existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Event not found with ID: {}", id);
                    return new ResourceNotFoundException("Event", "id", id);
                });
        BeanUtils.copyProperties(eventDTO, existingEvent, "id");
        eventRepository.save(existingEvent);
        return convertToResponseDTO(existingEvent);
    }


    public void delete(int id) {
        log.info("Deleting event with ID: {}", id);
        if (!eventRepository.existsById(id)) {
            log.error("Event not found with ID: {}", id);
            throw new ResourceNotFoundException("Event", "id", id);
        }
        eventRepository.deleteById(id);
    }


    public List<ApartmentResponseDTONoIdNoFk> getApartmentsByEventId(int id) {
        log.info("Retrieving apartments for event with ID: {}", id);
        if (!eventRepository.existsById(id)) {
            log.error("Event not found with ID: {}", id);
            throw new ResourceNotFoundException("Event", "id", id);
        }
        List<ApartmentEnt> apartments = apartmentRepository.findByEventId(id);
        return apartments.stream()
                .map(this::convertToApartmentResponseDTO)
                .collect(Collectors.toList());
    }

    private EventResponseDTO convertToResponseDTO(EventEnt event) {
        EventResponseDTO dto = new EventResponseDTO();
        BeanUtils.copyProperties(event, dto);
        return dto;
    }

    private ApartmentResponseDTONoIdNoFk convertToApartmentResponseDTO(ApartmentEnt apartment) {
        ApartmentResponseDTONoIdNoFk dto = new ApartmentResponseDTONoIdNoFk();
        BeanUtils.copyProperties(apartment, dto);
        return dto;
    }
}