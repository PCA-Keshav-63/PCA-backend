package com.businesslisting.pca.service;

import com.businesslisting.pca.model.ContactUs;
import com.businesslisting.pca.repository.ContactUsRepository;
import com.businesslisting.pca.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactUsService {

    @Autowired
    private ContactUsRepository contactUsRepository;

    public ContactUs save(ContactUs contactUs) {
        return contactUsRepository.save(contactUs);
    }

    public List<ContactUs> getAll() {
        return contactUsRepository.findAll();
    }

    public ContactUs getById(Long id) {
        return contactUsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContactUs not found with id: " + id));
    }

    public void delete(Long id) {
        ContactUs contactUs = getById(id);
        contactUsRepository.delete(contactUs);
    }
}