package com.businesslisting.pca.service;

import com.businesslisting.pca.dto.CreateServiceRequest;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
// import java.util.String;

public interface ServiceService {
String createService(CreateServiceRequest req, String email);
void uploadServiceImages(String serviceId, List<MultipartFile> images);
}