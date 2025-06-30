package com.businesslisting.pca.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "services")
@Data
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "CHAR(36)", unique = true, nullable = false)
    private String id;

    private String title;

    @Column(length = 1000)
    private String description;

    @Column(length = 1000)
    private String address;

    private Double lat;
    private Double lng;

    private String pincode;
    private String city;
    private String district;

    private Double priceFrom;
    private Double priceTo;

    private String timings;

    @Column(columnDefinition = "json")
    private String keywordsJson;

    @Column(columnDefinition = "json")
    private String phoneNumbersJson;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // @ManyToOne
    // @JoinColumn(name = "user_id")
    // private UserEntity owner;
    // 👇 Add mapping to UserEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @JsonIgnore
    private UserEntity owner;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ServiceImage> images;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;
}
