package com.businesslisting.pca.controller;

import com.businesslisting.pca.io.ProfileRequest;
import com.businesslisting.pca.io.ProfileResponse;
import com.businesslisting.pca.repository.UserRepository;
import com.businesslisting.pca.service.EmailService;
import com.businesslisting.pca.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

public class ProfileController {

    private final ProfileService profileService;
    private final EmailService emailService;

    @PostMapping("/api/v1.0/register")
    @ResponseStatus(HttpStatus.CREATED)

    public ProfileResponse register(@Valid @RequestBody ProfileRequest request) {
        System.out.println("Register request received: " + request);
        System.out.println("Register rikloahfkjahs received: " + request.getRole());

        System.out.println("Register role : " + request.getRole().getClass().getName());
        ProfileResponse response = profileService.createProfile(request);
        emailService.sendWelcomeEmail(response.getEmail(), response.getName(), response.getRole());
        return response;
    }

    @GetMapping("/api/v1.0/profile")
    public ProfileResponse getProfile(@CurrentSecurityContext(expression = "authentication?.name") String email) {
        profileService.getProfile(email);
        return profileService.getProfile(email);
    }

    @GetMapping("/")
    public ResponseEntity<?> root() {
        return ResponseEntity.ok("API is running");
    }
}
