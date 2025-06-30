package com.businesslisting.pca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.businesslisting.pca.model.Category;
import com.businesslisting.pca.model.Subcategory;

import java.util.List;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
    List<Subcategory> findByCategory_Id(Long categoryId);
}

