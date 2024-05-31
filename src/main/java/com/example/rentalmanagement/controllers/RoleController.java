package com.example.rentalmanagement.controllers;


import com.example.rentalmanagement.models.DTO.role.RoleRequestDTO;
import com.example.rentalmanagement.models.DTO.role.RoleResponseDTO;
import com.example.rentalmanagement.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/v1/roles")
@Tag(name = "Role Management", description = "Operations pertaining to roles in the Rental Management System")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @Operation(summary = "Retrieve all roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the roles",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoleResponseDTO.class)) })
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.findAll());
    }

    @Operation(summary = "Retrieve a role by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the role",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoleResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Role not found",
                    content = @Content)
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.findById(id));
    }

    @Operation(summary = "Create a new role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the role",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoleResponseDTO.class)) })
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleResponseDTO> createRole(@RequestBody @Valid RoleRequestDTO role) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.save(role));
    }

    @Operation(summary = "Update an existing role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the role",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoleResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Role not found",
                    content = @Content)
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleResponseDTO> updateRole(@PathVariable Integer id, @RequestBody @Valid RoleRequestDTO role) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.update(id, role));
    }

    @Operation(summary = "Delete a role by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted the role",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Role not found",
                    content = @Content)
    })
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteRole(@PathVariable Integer id) {
        roleService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}