package com.example.rentalmanagement.services;

import com.example.rentalmanagement.exceptions.ResourceNotFoundException;
import com.example.rentalmanagement.models.DTO.role.RoleRequestDTO;
import com.example.rentalmanagement.models.DTO.role.RoleResponseDTO;
import com.example.rentalmanagement.models.entities.RoleEnt;
import com.example.rentalmanagement.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService( RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }


    public List<RoleResponseDTO> findAll() {
        log.info("Fetching all roles");
        return roleRepository.findAll().stream()
                .map(this::convertToRoleResponseDTO)
                .collect(Collectors.toList());
    }

    public RoleResponseDTO findById(Integer id) {
        log.info("Fetching role by ID: {}", id);
        RoleEnt role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        return convertToRoleResponseDTO(role);
    }

    public RoleResponseDTO save(RoleRequestDTO roleRequestDTO) {
        log.info("Saving new role");
        RoleEnt role = new RoleEnt();
        BeanUtils.copyProperties(roleRequestDTO, role);
        roleRepository.save(role);
        return convertToRoleResponseDTO(role);
    }

    public RoleResponseDTO update(Integer id, RoleRequestDTO roleRequestDTO) {
        log.info("Updating role with ID: {}", id);
        RoleEnt role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        BeanUtils.copyProperties(roleRequestDTO, role);
        roleRepository.save(role);
        return convertToRoleResponseDTO(role);
    }

    public void delete(Integer id) {
        log.info("Deleting role with ID: {}", id);
        RoleEnt role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        roleRepository.delete(role);
    }

    private RoleResponseDTO convertToRoleResponseDTO(RoleEnt role) {
        RoleResponseDTO roleResponseDTO = new RoleResponseDTO();
        BeanUtils.copyProperties(role, roleResponseDTO);
        return roleResponseDTO;
    }
}
