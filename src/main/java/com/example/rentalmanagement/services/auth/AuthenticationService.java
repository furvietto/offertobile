package com.example.rentalmanagement.services.auth;

import com.example.rentalmanagement.config.JwtService;
import com.example.rentalmanagement.exceptions.*;
import com.example.rentalmanagement.models.DTO.auth.AuthenticationRequest;
import com.example.rentalmanagement.models.DTO.auth.AuthenticationResponse;
import com.example.rentalmanagement.models.DTO.auth.RegisterRequest;
import com.example.rentalmanagement.models.entities.RoleEnt;
import com.example.rentalmanagement.models.entities.UserEnt;
import com.example.rentalmanagement.repository.RoleRepository;
import com.example.rentalmanagement.repository.UserRepository;
import com.example.rentalmanagement.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthenticationResponse register(RegisterRequest request) {

        Integer role;

        if (!isNullOrEmpty(request.getIban()) || !isNullOrEmpty(request.getContractDetails()) || request.getContractDate() != null) {
            role = 2;
            if (isNullOrEmpty(request.getIban())) {
                throw new MissingValueException("iban");
            }
            if (isNullOrEmpty(request.getContractDetails())) {
                throw new MissingValueException("contractDetails");
            }
            if (request.getContractDate() == null) {
                throw new MissingValueException("contractDate");
            }
            if (!isValidIban(request.getIban())) {
                throw new MinOrMaxReachedException("IBAN");
            }
            if (!isValidContractDetails(request.getContractDetails())){
                throw new MaxCharacterReachedException("contract-date");
            }
        } else {
            role = 1;
        }


        RoleEnt roleEnt = roleRepository.findById(role).orElseThrow(
                () -> {
            log.error("Role not found with ID: {}", role);
            return new ResourceNotFoundException("Role", "id", role);
        });

        var user = UserEnt
                .builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .email(request.getEmail())
                .phone(request.getPhone())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .iban(request.getIban())
                .contractDate(request.getContractDate())
                .contractDetails(request.getContractDetails())
                .role(roleEnt)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> {
                    log.error("User not found with ID: {}", request.getUsername());
                    return new UsernameNotFoundException(request.getUsername());
                }
        );
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    // Metodo di utilità per validare l'IBAN
    private boolean isValidIban(String iban) {
        return iban != null && iban.length() >= 15 && iban.length() <= 34;
    }

    // Metodo di utilità per validare i dettagli del contratto
    private boolean isValidContractDetails(String contractDetails) {
        return contractDetails != null && contractDetails.length() <= 500;
    }
}
