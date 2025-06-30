package com.businesslisting.pca.service.impl;

import org.springframework.scheduling.annotation.Async;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.businesslisting.pca.dto.BusinessDTO;
import com.businesslisting.pca.exception.BusinessAlreadyExistsException;
import com.businesslisting.pca.mapper.BusinessMapper;
import com.businesslisting.pca.model.Business;
import com.businesslisting.pca.repository.BusinessRepository;
import com.businesslisting.pca.service.BusinessService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.businesslisting.pca.model.UserEntity;
import com.businesslisting.pca.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepository businessRepo;
    private final BusinessMapper businessMapper;
    private final Cloudinary cloudinary;
    private final UserRepository userRepository; // <-- Add this

    @Override
    public void registerBusiness(String email, BusinessDTO dto) {
        // Fetch the user by email
        UserEntity owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Map DTO to entity (without image URL)
        Business business = businessMapper.toEntity(dto);
        business.setProfilePictureUrl(null); // Placeholder or default

        // Set the owner
        business.setOwner(owner);

        // Save the business without image URL
        Business savedBusiness = businessRepo.save(business);
        System.out.println("Registering business Successfully: " + savedBusiness.getName());
        // Upload profile picture asynchronously
        uploadProfilePictureAsync(dto, savedBusiness.getId());
    }

    @Async
    public void uploadProfilePictureAsync(BusinessDTO dto, String businessId) {

        if (dto.getProfilePicture() == null || dto.getProfilePicture().isEmpty()) {
            return; // No file to upload
        }

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> uploadResult = (Map<String, Object>) cloudinary.uploader().upload(
                    dto.getProfilePicture().getBytes(),
                    ObjectUtils.emptyMap());

            String imageUrl = uploadResult.get("secure_url").toString();

            // Update the saved entity with the image URL
            businessRepo.findById(businessId).ifPresent(business -> {
                business.setProfilePictureUrl(imageUrl);
                businessRepo.save(business);
            });

        } catch (Exception e) {
            // Log the error or handle retry
            e.printStackTrace();
        }
    }
}
