package com.example.rentalmanagement.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "2158625d4a66683f21347233383c6f756e574942535326615e4c2e6b6e";
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Estrae un campo specifico dai claims del token JWT
    public <T> T extractClaim (String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    // Genera un token solo con i dettagli dell'utente
    public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(),userDetails);
    }

    // Genera un token con dettagli dell'utente e claims aggiuntivi
    public String generateToken (Map<String,Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.ES256)
                .compact();
    }

    // Valida il token (controlla se appartiene all'utente specificato)
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Controlla se il token è scaduto
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Estrae la data di scadenza dal token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Estrae tutti i claims dal token
    //here we extract all the claims (which are the three fields that make up the jwt: the header, the payload and verify signature and we also pass the key that the user needs to know that whoever sends the request is not an attacker)
    private Claims extractAllClaims(String token){
    return Jwts.parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    //here we decript the key which is above in this class
    private Key getSignInKey() {
        byte[] keyBites = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBites);
    }
}
