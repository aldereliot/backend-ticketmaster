package com.dev.tickets.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.private.key}")
    private String jwtKey;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    public String generateToken(String email){
        Algorithm algorithm = Algorithm.HMAC256(jwtKey);
        return JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    public String extractEmail(String token){
        DecodedJWT decoded = JWT.require(Algorithm.HMAC256(jwtKey))
                .build()
                .verify(token);
        return decoded.getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String email = extractEmail(token);
        return email != null && email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(jwtKey))
                .build().verify(token);
        return decodedJWT.getExpiresAt().before(new Date());
    }

}
