// package com.businesslisting.pca.model;


// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;
// import lombok.Data;


// @Data
// @Entity
// @Table(name = "businesses")
// public class Business {

//     @Id
//     @GeneratedValue(strategy = GenerationType.UUID)
//     @Column(name = "id", columnDefinition = "CHAR(36)", unique = true, nullable = false)
//     private String id;

//     private String name;
//     private String address;
//     private Double latitude;
//     private Double longitude;
//     private String phoneNumber;
//     private String profilePictureUrl;
// }

package com.businesslisting.pca.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name = "businesses")
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "CHAR(36)", unique = true, nullable = false)
    private String id;

    private String name;
    private String address;
    private String city;
    private String district;
    private String state;
    private String pincode;
    private Double latitude;
    private Double longitude;
    private String profilePictureUrl;
    private LocalDateTime createdAt = LocalDateTime.now();

    // 👇 Add mapping to UserEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @JsonIgnore
    private UserEntity owner;
    

}