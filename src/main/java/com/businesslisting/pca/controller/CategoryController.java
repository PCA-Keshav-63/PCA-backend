package com.businesslisting.pca.controller;

import com.businesslisting.pca.model.Category;
import com.businesslisting.pca.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/categories")
@RequiredArgsConstructor
@CrossOrigin // Allow requests from frontend (optional)
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        System.out.println("Fetching all categories");
        return categoryService.getAllCategories();
    }
}

