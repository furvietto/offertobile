package com.example.rentalmanagement.controllers;


import com.example.rentalmanagement.models.DTO.apartment.ApartmentResponseDTONoIdNoFk;
import com.example.rentalmanagement.models.DTO.event.EventRequestDTONoId;
import com.example.rentalmanagement.models.DTO.event.EventResponseDTO;
import com.example.rentalmanagement.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/v1/events")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Event Management", description = "Operations pertaining to events in the Rental Management System")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Retrieve all events.
     * @return List of all events.
     */
    @Operation(summary = "Retrieve all events")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the events",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventResponseDTO.class)) })
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'OWNER')")
    @RequestMapping(
            path = "getAllEvents",
            method = RequestMethod.GET,
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
    @Operation(summary = "Retrieve an event by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the event",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content)
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'OWNER')")
    @RequestMapping(
            path = "getEventById/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.findById(id));
    }

    /**
     * Create a new event.
     * @param event DTO containing event details.
     * @return Created event details.
     */
    @Operation(summary = "Create a new event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the event",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventResponseDTO.class)) })
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
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
    @Operation(summary = "Update an existing event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the event",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @RequestMapping(
            path = "updateEvent/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EventResponseDTO> updateEvent(@PathVariable Integer id, @RequestBody @Valid EventRequestDTONoId event) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.update(id, event));
    }

    /**
     * Delete an event by its ID.
     * @param id ID of the event to delete.
     * @return No content.
     */
    @Operation(summary = "Delete an event by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted the event",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(
            path = "deleteEvent/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer id) {
        eventService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Retrieve apartments by event ID.
     * @param id ID of the event.
     * @return List of apartments associated with the event.
     */
    @Operation(summary = "Retrieve apartments by event ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the apartments",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApartmentResponseDTONoIdNoFk.class)) }),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content)
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'OWNER')")
    @RequestMapping(
            path = "event/{id}/apartments",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ApartmentResponseDTONoIdNoFk>> getApartmentsByEventId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getApartmentsByEventId(id));
    }
}
