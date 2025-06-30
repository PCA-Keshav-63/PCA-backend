package com.businesslisting.pca.service;

import java.io.IOException;

import com.businesslisting.pca.dto.BusinessDTO;

public interface BusinessService {
    void registerBusiness(String email, BusinessDTO dto) throws IOException;
}