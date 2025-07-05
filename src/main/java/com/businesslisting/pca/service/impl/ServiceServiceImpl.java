package com.businesslisting.pca.service.impl;

import com.businesslisting.pca.dto.CreateServiceRequest;
import com.businesslisting.pca.model.*;
import com.businesslisting.pca.repository.*;
import com.businesslisting.pca.service.ServiceService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import net.coobird.thumbnailator.Thumbnails;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ServiceImageRepository serviceImageRepository;
    private final Cloudinary cloudinary; // Inject Cloudinary bean

    // @Override
    // public String createService(CreateServiceRequest req, String email) {
    // UserEntity user = userRepository.findByEmail(email).orElseThrow();
    // Business business = businessRepository.findByOwner(user).orElseThrow();
    // Category category =
    // categoryRepository.findById(req.getCategoryId()).orElseThrow();
    // Subcategory subcategory =
    // subcategoryRepository.findById(req.getSubcategoryId()).orElseThrow();

    // ServiceEntity service = new ServiceEntity();
    // service.setTitle(req.getTitle());
    // service.setDescription(req.getDescription());
    // service.setAddress(req.getAddress() != null ? req.getAddress() :
    // business.getAddress());
    // service.setCity(req.getCity() != null ? req.getCity() : business.getCity());
    // service.setDistrict(req.getDistrict() != null ? req.getDistrict() :
    // business.getDistrict());
    // service.setPincode(req.getPincode() != null ? req.getPincode() :
    // business.getPincode());
    // service.setLat(req.getLat() != null ? req.getLat() : business.getLatitude());
    // service.setLng(req.getLng() != null ? req.getLng() :
    // business.getLongitude());
    // service.setPhoneNumbersJson(req.getPhoneNumbersJson() != null ?
    // req.getPhoneNumbersJson() : "null");
    // service.setKeywordsJson(req.getKeywordsJson());
    // service.setPriceFrom(req.getPriceFrom());
    // service.setPriceTo(req.getPriceTo());
    // service.setTimings(req.getTimings());
    // service.setOwner(user);
    // service.setCategory(category);
    // service.setSubcategory(subcategory);

    // ServiceEntity savedService = serviceRepository.save(service);
    // return savedService.getId();
    // }
    @Override
    public String createService(CreateServiceRequest req, String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        Business business = businessRepository.findByOwner(user)
                .orElseThrow(() -> new RuntimeException("Business not found for user: " + user.getId()));
        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + req.getCategoryId()));
        Subcategory subcategory = subcategoryRepository.findById(req.getSubcategoryId())
                .orElseThrow(() -> new RuntimeException("Subcategory not found with id: " + req.getSubcategoryId()));

        ServiceEntity service = new ServiceEntity();
        service.setTitle(req.getTitle());
        service.setDescription(req.getDescription());
        service.setAddress(req.getAddress() != null ? req.getAddress() : business.getAddress());
        service.setCity(req.getCity() != null ? req.getCity() : business.getCity());
        service.setDistrict(req.getDistrict() != null ? req.getDistrict() : business.getDistrict());
        service.setPincode(req.getPincode() != null ? req.getPincode() : business.getPincode());
        service.setLat(req.getLat() != null ? req.getLat() : business.getLatitude());
        service.setLng(req.getLng() != null ? req.getLng() : business.getLongitude());
        service.setPhoneNumbersJson(req.getPhoneNumbersJson() != null ? req.getPhoneNumbersJson() : "null");
        service.setKeywordsJson(req.getKeywordsJson());
        service.setPriceFrom(req.getPriceFrom());
        service.setPriceTo(req.getPriceTo());
        service.setTimings(req.getTimings());
        service.setOwner(user);
        service.setBusiness(business); // <-- set business here
        service.setCategory(category);
        service.setSubcategory(subcategory);

        ServiceEntity savedService = serviceRepository.save(service);
        return savedService.getId();
    }

    @Override
    public void uploadServiceImages(String serviceId, List<MultipartFile> images) {
        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        List<ServiceImage> serviceImages = images.parallelStream().map(image -> {
            try (InputStream in = image.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

                // Resize to max 1024x1024 and compress to 80% quality
                Thumbnails.of(in)
                        .size(1024, 1024)
                        .outputQuality(0.8)
                        .toOutputStream(baos);

                byte[] resizedBytes = baos.toByteArray();

                var uploadResult = cloudinary.uploader().upload(resizedBytes, ObjectUtils.emptyMap());
                String imageUrl = (String) uploadResult.get("secure_url");

                ServiceImage serviceImage = new ServiceImage();
                serviceImage.setService(service);
                serviceImage.setImageUrl(imageUrl);
                return serviceImage;

            } catch (Exception e) {
                throw new RuntimeException("Failed to upload image", e);
            }
        }).toList();

        serviceImageRepository.saveAll(serviceImages);
    }
}
