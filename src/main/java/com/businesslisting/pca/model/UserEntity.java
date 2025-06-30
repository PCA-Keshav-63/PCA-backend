package com.businesslisting.pca.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

// import com.businesslisting.pca.io.ProfileResponse.ProfileResponseBuilder;

import io.micrometer.common.lang.NonNull;

import java.sql.Timestamp;

@Entity
@Table(name = "tbl_users")
@Data
@Builder
@NoArgsConstructor // <-- Add this line
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String userId;

    private String name;

    @NonNull
    @Column(unique = true)
    private String email;

    private String password;
    private String phoneNumber;
    private String verifyOtp;
    private Boolean isAccountVerified;
    private long verifyOtpExpireAt;
    private String resetOtp;
    private long resetOtpExpireAt;

  
    private String role; 
    
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    // public static ProfileResponseBuilder builder() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'builder'");
    // }
}
