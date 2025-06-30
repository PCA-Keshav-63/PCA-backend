package com.businesslisting.pca.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BusinessDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;
    private String address;
    private String city;
    private String district;
    private String state;
    private String pincode;
    private Double latitude;
    private Double longitude;
    private String phoneNumber;
    private MultipartFile profilePicture;
    private String profilePictureUrl; // For response, not for input
    private String userId; // <-- Add this line
}
