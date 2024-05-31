package com.example.rentalmanagement.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
      http
              .csrf(AbstractHttpConfigurer::disable)//Disabilita la protezione CSRF
              .authorizeHttpRequests(authorize -> authorize
                      .requestMatchers("").permitAll()  // Permetti accesso pubblico a specifici endpoint
                      .anyRequest().authenticated()  // Richiedi autenticazione per tutti gli altri endpoint
              )
              .sessionManagement(session -> session
                      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Gestione della sessione senza stato
              )
              .authenticationProvider(authenticationProvider) // Configura il provider di autenticazione
              .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Aggiungi il filtro JWT prima del filtro di autenticazione delle credenziali username/password
        return http.build();
    }
}
