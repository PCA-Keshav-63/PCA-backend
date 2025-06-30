package com.businesslisting.pca.dto;

import lombok.Data;

@Data
public class CreateServiceRequest {
    private String title;
    private String description;
    private String address;
    private String city;
    private String district;
    private String pincode;
    private Double lat;
    private Double lng;
    private Double priceFrom;
    private Double priceTo;
    private String timings;
    private String phoneNumbersJson;
    private String keywordsJson;
    private Long categoryId;
    private Long subcategoryId;
}
