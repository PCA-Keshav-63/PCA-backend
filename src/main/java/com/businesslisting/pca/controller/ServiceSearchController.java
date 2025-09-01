package com.businesslisting.pca.controller;

import com.businesslisting.pca.model.ServiceEntity;
import com.businesslisting.pca.repository.ServiceEntityRepository;
import com.businesslisting.pca.service.ServiceSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/services")
@RequiredArgsConstructor
public class ServiceSearchController {
    private final ServiceSearchService searchService;
    private final ServiceEntityRepository serviceEntityRepository; // <-- Add this line

    @GetMapping("/search")
    public List<ServiceEntity> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String pincode,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) String subcategoryId,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String subcategoryName,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String businessName,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lng,
            @RequestParam(required = false) Double radiusKm) {
        return searchService.search(
                q, pincode, city, district, categoryId, subcategoryId,
                categoryName, subcategoryName, keyword, title, businessName,
                lat, lng, radiusKm);
    }

    @GetMapping("/nearby")
    public List<ServiceEntity> getNearbyServices(
    @RequestParam Double lat,
    @RequestParam Double lng,
    @RequestParam(defaultValue = "10") Double radiusKm
    ) {
    return serviceEntityRepository.findNearby(lat, lng, radiusKm);
    }

    @GetMapping("/autocomplete/{field}")
    public List<String> autocompleteField(@PathVariable String field, @RequestParam String query) {
        return searchService.autocompleteField(field, query);
    }
}
