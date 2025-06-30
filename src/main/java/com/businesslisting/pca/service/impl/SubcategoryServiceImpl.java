package com.businesslisting.pca.service.impl;

import com.businesslisting.pca.model.Subcategory;
import com.businesslisting.pca.repository.SubcategoryRepository;
import com.businesslisting.pca.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;

    @Override
    public List<Subcategory> getByCategoryId(Long categoryId) {
        return subcategoryRepository.findByCategory_Id(categoryId);
    }
}
