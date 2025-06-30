package com.businesslisting.pca.mapper;

import org.springframework.stereotype.Component;

import com.businesslisting.pca.dto.BusinessDTO;
import com.businesslisting.pca.model.Business;

@Component
public class BusinessMapper {

    public Business toEntity(BusinessDTO dto) {
    Business business = new Business();
    business.setName(dto.getName());
    business.setAddress(dto.getAddress());
    business.setCity(dto.getCity());
    business.setDistrict(dto.getDistrict());
    business.setState(dto.getState());
    business.setPincode(dto.getPincode());
    business.setLatitude(dto.getLatitude());
    business.setLongitude(dto.getLongitude());
    // business.setProfilePictureUrl(...) // Set after upload

    // Owner should be set in the service layer using dto.getUserId()
    return business;
    }
    

    public BusinessDTO toDTO(Business business) {
    BusinessDTO dto = new BusinessDTO();
    dto.setName(business.getName());
    dto.setAddress(business.getAddress());
    dto.setCity(business.getCity());
    dto.setDistrict(business.getDistrict());
    dto.setState(business.getState());
    dto.setPincode(business.getPincode());
    dto.setLatitude(business.getLatitude());
    dto.setLongitude(business.getLongitude());
    dto.setProfilePictureUrl(business.getProfilePictureUrl());

    // Set userId from owner if present
    if (business.getOwner() != null) {
        dto.setUserId(business.getOwner().getUserId());
    }

    return dto;
    }
}
