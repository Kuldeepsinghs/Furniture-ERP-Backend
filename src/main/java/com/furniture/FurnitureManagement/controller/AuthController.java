package com.furniture.FurnitureManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

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

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.authenticationManager =
                authenticationManager;

        this.jwtService =
                jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse>
    login(
            @RequestBody
            AuthRequest request) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        String token =
                jwtService.generateToken(
                        request.getUsername());

        return ResponseEntity.ok(
                new AuthResponse(token));
    }
}

