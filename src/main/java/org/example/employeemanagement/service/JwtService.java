package org.example.employeemanagement.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.employeemanagement.entity.model.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {
    @Value("${spring.security.jwt.secret}")
    private String SECRET_KEY;

    @Value("${spring.security.jwt.token-expiration}")
    private long tokenExpiration;

    public String generateToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .setId(UUID.randomUUID().toString())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getBody()
                .getSubject();
    }

    public Date extractDateExpiration(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getBody()
                .getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return  extractDateExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, CustomUserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
