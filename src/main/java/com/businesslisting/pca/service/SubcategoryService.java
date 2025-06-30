package com.businesslisting.pca.service;

import com.businesslisting.pca.model.Subcategory;

import java.util.List;

public interface SubcategoryService {
    List<Subcategory> getByCategoryId(Long categoryId);
}

