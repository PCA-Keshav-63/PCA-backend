package com.businesslisting.pca.controller;

import com.businesslisting.pca.model.ContactUs;
import com.businesslisting.pca.service.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/contact-us")
public class ContactUsController {

    @Autowired
    private ContactUsService contactUsService;
    @PostMapping
    public ResponseEntity<ContactUs> create(@RequestBody ContactUs contactUs) {
        System.out.println("ContactUsController api");
        return ResponseEntity.ok(contactUsService.save(contactUs));
    }

    @GetMapping
    public ResponseEntity<List<ContactUs>> getAll() {
        return ResponseEntity.ok(contactUsService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactUs> getById(@PathVariable Long id) {
        return ResponseEntity.ok(contactUsService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contactUsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}