package com.example.rentalmanagement.controllers;


import com.example.rentalmanagement.models.DTO.apartment.ApartmentResponseDTONoIdNoFk;
import com.example.rentalmanagement.models.DTO.event.EventRequestDTONoId;
import com.example.rentalmanagement.models.DTO.event.EventResponseDTO;
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
@RequestMapping("/v1/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Retrieve all events.
     * @return List of all events.
     */
    @RequestMapping(
            path = "getAllEvents",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<EventResponseDTO>> getAllEvents() {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.findAll());
    }

    /**
     * Retrieve an event by its ID.
     * @param id ID of the event.
     * @return Event details.
     */
    @RequestMapping(
            path = "getEventById/{id}",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.findById(id));
    }

    /**
     * Create a new event.
     * @param event DTO containing event details.
     * @return Created event details.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EventResponseDTO> createEvent(@RequestBody @Valid EventRequestDTONoId event) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.save(event));
    }

    /**
     * Update an existing event.
     * @param id ID of the event to update.
     * @param event DTO containing updated event details.
     * @return Updated event details.
     */
    @RequestMapping(
            path = "updateEvent/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EventResponseDTO> updateEvent(@PathVariable int id, @RequestBody @Valid EventRequestDTONoId event) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.update(id, event));
    }

    /**
     * Delete an event by its ID.
     * @param id ID of the event to delete.
     * @return No content.
     */
    @RequestMapping(
            path = "deleteEvent/{id}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deleteEvent(@PathVariable int id) {
        eventService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Retrieve apartments by event ID.
     * @param id ID of the event.
     * @return List of apartments associated with the event.
     */
    @GetMapping("event/{id}/apartments")
    public ResponseEntity<List<ApartmentResponseDTONoIdNoFk>> getApartmentsByEventId(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getApartmentsByEventId(id));
    }
}
