package com.businesslisting.pca.controller;

import com.businesslisting.pca.model.Subcategory;
import com.businesslisting.pca.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subcategories")
@CrossOrigin // Optional: For local frontend to call this
@RequiredArgsConstructor
public class SubcategoryController {

    private final SubcategoryService subcategoryService;

    @GetMapping
    public List<Subcategory> getSubcategories(@RequestParam Long categoryId) {
        return subcategoryService.getByCategoryId(categoryId);
    }
}

