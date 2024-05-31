package com.example.rentalmanagement.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //here is the first process, we check the JWT token

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
           @NonNull FilterChain filterChain
    ) throws ServletException, IOException
    {

        //here we took the jwt token

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        //if authHeader is null or it doesn't start with bearer we pass the request and response to the next filter

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        // now we extract the jwt token starting from seven cause "Bearer " has 7 string

        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);

        //here we check if the user is authenticated, if goes in the if he is not authenticated yet

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            //now we check if the token is still valid or not

            if (jwtService.isTokenValid(jwt,userDetails)){

                //now we need to create the security context and we pass null to credentials cause user still don't have credential when goes in our site

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                //add more details to authtoken
                authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Imposta il contesto di sicurezza con il token di autenticazione
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
