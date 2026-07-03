package com.furniture.FurnitureManagement.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private Key getSignKey() {

        return Keys.hmacShaKeyFor(
                secret.getBytes(
                        StandardCharsets.UTF_8));
    }

    public String generateToken(
            String username) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                + 1000 * 60 * 60 * 24))
                .signWith(
                        getSignKey())
                .compact();
    }

    public String generateToken(
            UserDetails userDetails) {

        List<String> roles =
                userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .subject(
                        userDetails.getUsername())
                .claim(
                        "roles",
                        roles)
                .claim(
                        "role",
                        roles.isEmpty()
                        ? null
                        : roles.get(0))
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                + 1000 * 60 * 60 * 24))
                .signWith(
                        getSignKey())
                .compact();
    }

    public String extractUsername(
            String token) {

        return Jwts.parser()
                .verifyWith(
                        (SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(
            String token) {

        Object roles =
                Jwts.parser()
                .verifyWith(
                        (SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("roles");

        if (roles instanceof List<?>) {

            return ((List<?>) roles)
                    .stream()
                    .map(String::valueOf)
                    .toList();
        }

        return List.of();
    }

    public boolean validateToken(
            String token,
            String username) {

        return extractUsername(token)
                .equals(username);
    }
}
