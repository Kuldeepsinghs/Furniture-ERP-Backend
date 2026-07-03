package com.furniture.FurnitureManagement.controller;


import org.springframework.security.core.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.furniture.FurnitureManagement.dto.AuthRequest;
import com.furniture.FurnitureManagement.dto.AuthResponse;
import com.furniture.FurnitureManagement.security.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager
            authenticationManager;

    private final JwtService
            jwtService;

    private final UserDetailsService
            userDetailsService;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserDetailsService userDetailsService) {

        this.authenticationManager =
                authenticationManager;

        this.jwtService =
                jwtService;

        this.userDetailsService =
                userDetailsService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(request.getUsername());

            String token = jwtService.generateToken(userDetails);

            String role = userDetails.getAuthorities()
                    .stream()
                    .findFirst()
                    .map(authority -> authority.getAuthority().replace("ROLE_", ""))
                    .orElse(null);

            return ResponseEntity.ok(
                    new AuthResponse(token, userDetails.getUsername(), role));

        } catch (AuthenticationException ex) {
            // Return a clean 401 with a real message instead of letting
            // Spring Security's default entry point return a bare
            // 403 "Access Denied", which the frontend was misreading
            // as an expired session.
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(java.util.Map.of("message", "Invalid username or password."));
        }
    }
}

