package com.businesslisting.pca.repository;

import com.businesslisting.pca.model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceEntityRepository
        extends JpaRepository<ServiceEntity, String>, JpaSpecificationExecutor<ServiceEntity> {
    // For autocomplete (distinct titles, business names, etc.)
    // @Query("SELECT DISTINCT s.title FROM ServiceEntity s WHERE LOWER(s.title) LIKE LOWER(CONCAT(:query, '%'))")
    // List<String> autocompleteTitle(String query);

    // @Query("SELECT DISTINCT s.business.name FROM ServiceEntity s WHERE LOWER(s.business.name) LIKE LOWER(CONCAT(:query, '%'))")
    // List<String> autocompleteBusinessName(String query);

    @Query(value = """
                SELECT * FROM services
                WHERE (
                    6371 * acos(
                        cos(radians(:lat)) * cos(radians(lat)) *
                        cos(radians(lng) - radians(:lng)) +
                        sin(radians(:lat)) * sin(radians(lat))
                    )
                ) <= :radiusKm
            """, nativeQuery = true)
    List<ServiceEntity> findNearby(Double lat, Double lng, Double radiusKm);

    // @Query("SELECT DISTINCT s.title FROM ServiceEntity s WHERE LOWER(s.title) LIKE LOWER(CONCAT(:query, '%'))")
    // List<String> autocompleteTitle(@Param("query") String query);

    // @Query("SELECT DISTINCT s.city FROM ServiceEntity s WHERE LOWER(s.city) LIKE LOWER(CONCAT(:query, '%'))")
    // List<String> autocompleteCity(@Param("query") String query);

    // @Query("SELECT DISTINCT s.district FROM ServiceEntity s WHERE LOWER(s.district) LIKE LOWER(CONCAT(:query, '%'))")
    // List<String> autocompleteDistrict(@Param("query") String query);

    // @Query("SELECT DISTINCT s.pincode FROM ServiceEntity s WHERE s.pincode LIKE CONCAT(:query, '%')")
    // List<String> autocompletePincode(@Param("query") String query);

    // @Query("SELECT DISTINCT s.address FROM ServiceEntity s WHERE LOWER(s.address) LIKE LOWER(CONCAT(:query, '%'))")
    // List<String> autocompleteAddress(@Param("query") String query);

    @Query("SELECT DISTINCT s.title FROM ServiceEntity s WHERE LOWER(s.title) LIKE LOWER(CONCAT(:query, '%'))")
    List<String> autocompleteTitle(@Param("query") String query);

    @Query("SELECT DISTINCT s.city FROM ServiceEntity s WHERE LOWER(s.city) LIKE LOWER(CONCAT(:query, '%'))")
    List<String> autocompleteCity(@Param("query") String query);

    @Query("SELECT DISTINCT s.district FROM ServiceEntity s WHERE LOWER(s.district) LIKE LOWER(CONCAT(:query, '%'))")
    List<String> autocompleteDistrict(@Param("query") String query);

    @Query("SELECT DISTINCT s.pincode FROM ServiceEntity s WHERE s.pincode LIKE CONCAT(:query, '%')")
    List<String> autocompletePincode(@Param("query") String query);

    @Query("SELECT DISTINCT s.address FROM ServiceEntity s WHERE LOWER(s.address) LIKE LOWER(CONCAT(:query, '%'))")
    List<String> autocompleteAddress(@Param("query") String query);

    @Query("SELECT DISTINCT s.business.name FROM ServiceEntity s WHERE LOWER(s.business.name) LIKE LOWER(CONCAT(:query, '%'))")
    List<String> autocompleteBusinessName(@Param("query") String query);

    @Query("SELECT DISTINCT s.category.name FROM ServiceEntity s WHERE LOWER(s.category.name) LIKE LOWER(CONCAT(:query, '%'))")
    List<String> autocompleteCategoryName(@Param("query") String query);

    @Query("SELECT DISTINCT s.subcategory.name FROM ServiceEntity s WHERE LOWER(s.subcategory.name) LIKE LOWER(CONCAT(:query, '%'))")
    List<String> autocompleteSubcategoryName(@Param("query") String query);

@Query("SELECT DISTINCT s.keywordsJson FROM ServiceEntity s")
List<String> findAllKeywordsJson();

}