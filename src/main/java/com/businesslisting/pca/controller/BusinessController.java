package com.businesslisting.pca.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.businesslisting.pca.dto.BusinessDTO;
import com.businesslisting.pca.exception.BusinessAlreadyExistsException;
import com.businesslisting.pca.service.BusinessService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1.0/businesses")
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessService businessService;

    @PostMapping("/register")
    public ResponseEntity<String> registerBusiness(
            @CurrentSecurityContext(expression = "authentication?.name") String email,
            @Validated @ModelAttribute BusinessDTO dto) throws IOException {
        // Logging for debug
        System.out.println("Received business registration request from email: " + email);
        System.out.println("Registering business: " + dto.getName());
        System.out.println("Address: " + dto.getAddress());
        System.out.println("City: " + dto.getCity());
        System.out.println("District: " + dto.getDistrict());
        System.out.println("State: " + dto.getState());
        System.out.println("Pincode: " + dto.getPincode());
        System.out.println("Latitude: " + dto.getLatitude());
        System.out.println("Longitude: " + dto.getLongitude());
        System.out.println("Profile Picture Filename: " +
                (dto.getProfilePicture() != null ? dto.getProfilePicture().getOriginalFilename() : "N/A"));

        businessService.registerBusiness(email, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Business registered successfully");
    }

    @ExceptionHandler(BusinessAlreadyExistsException.class)
    public ResponseEntity<String> handleBusinessAlreadyExists(BusinessAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + ex.getMessage());
    }
}
