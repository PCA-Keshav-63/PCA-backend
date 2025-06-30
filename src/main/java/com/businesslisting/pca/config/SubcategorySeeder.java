// package com.businesslisting.pca.config;


// import com.businesslisting.pca.model.Category;
// import com.businesslisting.pca.model.Subcategory;
// import com.businesslisting.pca.repository.CategoryRepository;
// import com.businesslisting.pca.repository.SubcategoryRepository;
// import lombok.RequiredArgsConstructor;
// import org.apache.poi.ss.usermodel.*;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import java.io.InputStream;
// import java.util.HashSet;
// import java.util.Set;

// @Component
// @RequiredArgsConstructor
// public class SubcategorySeeder implements CommandLineRunner {

//     private final CategoryRepository categoryRepository;
//     private final SubcategoryRepository subcategoryRepository;

//     @Override
//     public void run(String... args) throws Exception {
//         InputStream is = getClass().getClassLoader().getResourceAsStream("data/Categorized_Services.xlsx");

//         if (is == null) {
//             System.err.println("❌ File not found in resources/data");
//             return;
//         }

//         Workbook workbook = new XSSFWorkbook(is);
//         Sheet sheet = workbook.getSheetAt(0);
//         Set<String> inserted = new HashSet<>();

//         for (Row row : sheet) {
//             if (row.getRowNum() == 0) continue;

//             String categoryName = row.getCell(0).getStringCellValue().trim();
//             String subcategoryName = row.getCell(1).getStringCellValue().trim();

//             if (categoryName.isEmpty() || subcategoryName.isEmpty()) continue;

//             Category category = categoryRepository.findByName(categoryName);
//             if (category == null) {
//                 System.out.println("⚠️ Category not found: " + categoryName);
//                 continue;
//             }

//             String key = categoryName + "::" + subcategoryName;
//             if (!inserted.contains(key) && !subcategoryRepository.existsByNameAndCategory(subcategoryName, category)) {
//                 Subcategory sub = new Subcategory();
//                 sub.setName(subcategoryName);
//                 sub.setCategory(category);
//                 subcategoryRepository.save(sub);
//                 inserted.add(key);
//             }
//         }

//         System.out.println("✅ Subcategories seeded successfully.");
//     }
// }

