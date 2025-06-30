package com.businesslisting.pca.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.businesslisting.pca.model.Business;
import com.businesslisting.pca.model.UserEntity;

public interface BusinessRepository extends JpaRepository<Business, String> {
    // boolean existsByName(String name);
    // ...existing code...
Optional<Business> findByOwner(UserEntity owner);
// ...existing code...
}