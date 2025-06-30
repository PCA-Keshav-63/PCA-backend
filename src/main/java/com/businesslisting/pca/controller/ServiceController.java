package com.businesslisting.pca.controller;

import com.businesslisting.pca.dto.CreateServiceRequest;
import com.businesslisting.pca.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    // @PostMapping("/create")
    // public ResponseEntity<String> createService(
    // @CurrentSecurityContext(expression = "authentication?.name") String email,
    // @Validated @ModelAttribute CreateServiceMultipartRequest request,
    // @RequestParam(value = "images", required = false) List<MultipartFile> images
    // ) {
    // serviceService.createServiceWithImages(request, images, email);
    // return ResponseEntity.status(HttpStatus.CREATED).body("Service listed
    // successfully");
    // }
@PostMapping("/create")
public ResponseEntity<String> createService(
        @CurrentSecurityContext(expression = "authentication?.name") String email,
        @RequestBody CreateServiceRequest request) {
    String serviceId = serviceService.createService(request, email);
    return ResponseEntity.status(HttpStatus.CREATED).body(serviceId);
}

    @PostMapping("/{serviceId}/images")
    public ResponseEntity<String> uploadImages(
            @PathVariable String serviceId,
            @RequestParam("images") List<MultipartFile> images) {
        serviceService.uploadServiceImages(serviceId, images);
        return ResponseEntity.ok("Images uploaded successfully");
    }
}