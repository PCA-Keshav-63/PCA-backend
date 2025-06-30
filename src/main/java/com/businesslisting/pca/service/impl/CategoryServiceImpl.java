package com.businesslisting.pca.service.impl;



import com.businesslisting.pca.model.Category;
import com.businesslisting.pca.repository.CategoryRepository;
import com.businesslisting.pca.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Cacheable(value = "categories")
    public List<Category> getAllCategories() {
        System.out.println("⏳ Fetching categories from database...");
        return categoryRepository.findAll();
    }
}

