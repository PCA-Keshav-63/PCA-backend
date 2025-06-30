package com.businesslisting.pca.repository;

import com.businesslisting.pca.model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
// import java.util.UUID;

public interface ServiceRepository extends JpaRepository<ServiceEntity, String> {
    List<ServiceEntity> findByCategory_IdAndCityContainingIgnoreCase(Long categoryId, String city);
    List<ServiceEntity> findByCategory_Id(Long categoryId);
    List<ServiceEntity> findByCityContainingIgnoreCase(String city);
    
}
