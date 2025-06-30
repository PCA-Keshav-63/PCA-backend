// package com.businesslisting.pca.service;

// import com.businesslisting.pca.model.ServiceEntity;
// import com.businesslisting.pca.repository.ServiceEntityRepository;
// import jakarta.persistence.criteria.*;
// import lombok.RequiredArgsConstructor;
// import org.springframework.data.jpa.domain.Specification;
// import org.springframework.stereotype.Service;

// import java.util.ArrayList;
// import java.util.List;

// @Service
// @RequiredArgsConstructor
// public class ServiceSearchService {
//     private final ServiceEntityRepository repository;

//     public List<ServiceEntity> search(
//             String pincode, String city, String district, String categoryId, String subcategoryId,
//             String keyword, String title, String businessName, Double lat, Double lng, Double radiusKm
//     ) {
//         Specification<ServiceEntity> spec = (root, query, cb) -> {
//             List<Predicate> predicates = new ArrayList<>();
//             if (pincode != null) predicates.add(cb.equal(root.get("pincode"), pincode));
//             if (city != null) predicates.add(cb.equal(cb.lower(root.get("city")), city.toLowerCase()));
//             if (district != null) predicates.add(cb.equal(cb.lower(root.get("district")), district.toLowerCase()));
//             if (categoryId != null) predicates.add(cb.equal(root.get("category").get("id"), categoryId));
//             if (subcategoryId != null) predicates.add(cb.equal(root.get("subcategory").get("id"), subcategoryId));
//             if (title != null) predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
//             if (businessName != null) predicates.add(cb.like(cb.lower(root.get("owner").get("businessName")), "%" + businessName.toLowerCase() + "%"));
//             if (keyword != null) predicates.add(cb.like(cb.lower(root.get("keywordsJson")), "%" + keyword.toLowerCase() + "%"));
//             // Live location (simple radius search using Haversine formula)
//             if (lat != null && lng != null && radiusKm != null) {
//                 Expression<Double> latExpr = root.get("lat");
//                 Expression<Double> lngExpr = root.get("lng");
//                 Expression<Double> distance = cb.function("6371 * acos", Double.class,
//                         cb.sum(
//                                 cb.prod(
//                                         cb.cos(cb.toRadians(cb.literal(lat))),
//                                         cb.cos(cb.toRadians(latExpr))
//                                 ),
//                                 cb.prod(
//                                         cb.sin(cb.toRadians(cb.literal(lat))),
//                                         cb.sin(cb.toRadians(latExpr))
//                                 ),
//                                 cb.prod(
//                                         cb.cos(cb.toRadians(latExpr)),
//                                         cb.cos(cb.toRadians(cb.literal(lng) - lngExpr))
//                                 )
//                         )
//                 );
//                 predicates.add(cb.le(distance, radiusKm));
//             }
//             return cb.and(predicates.toArray(new Predicate[0]));
//         };
//         return repository.findAll(spec);
//     }

//     public List<String> autocompleteTitle(String query) {
//         return repository.autocompleteTitle(query);
//     }

//     public List<String> autocompleteBusinessName(String query) {
//         return repository.autocompleteBusinessName(query);
//     }
// }

package com.businesslisting.pca.service;

import com.businesslisting.pca.model.ServiceEntity;
import com.businesslisting.pca.repository.ServiceEntityRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ServiceSearchService {
    private final ServiceEntityRepository repository;

    // public List<ServiceEntity> search(
    //         String pincode, String city, String district, String categoryId, String subcategoryId,String categoryName, String subcategoryName,
    //         String keyword, String title, String businessName, Double lat, Double lng, Double radiusKm) {
    //     Specification<ServiceEntity> spec = (root, query, cb) -> {
    //         List<Predicate> predicates = new ArrayList<>();
    //         if (pincode != null && !pincode.isEmpty())
    //             predicates.add(cb.equal(root.get("pincode"), pincode));
    //         if (city != null && !city.isEmpty())
    //             predicates.add(cb.equal(cb.lower(root.get("city")), city.toLowerCase()));
    //         if (district != null && !district.isEmpty())
    //             predicates.add(cb.equal(cb.lower(root.get("district")), district.toLowerCase()));
    //     if (categoryId != null && !categoryId.isEmpty())
    //         predicates.add(cb.equal(root.get("category").get("id"), Long.valueOf(categoryId)));
    //     if (categoryName != null && !categoryName.isEmpty())
    //         predicates.add(cb.like(cb.lower(root.get("category").get("name")), "%" + categoryName.toLowerCase() + "%"));
    //     if (subcategoryId != null && !subcategoryId.isEmpty())
    //         predicates.add(cb.equal(root.get("subcategory").get("id"), Long.valueOf(subcategoryId)));
    //     if (subcategoryName != null && !subcategoryName.isEmpty())
    //         predicates.add(cb.like(cb.lower(root.get("subcategory").get("name")), "%" + subcategoryName.toLowerCase() + "%"));
    //         if (title != null && !title.isEmpty())
    //             predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
    //         if (businessName != null && !businessName.isEmpty())
    //             predicates.add(cb.like(cb.lower(root.get("owner").get("name")), "%" + businessName.toLowerCase() + "%"));
    //         if (keyword != null && !keyword.isEmpty())
    //             predicates.add(cb.like(cb.lower(root.get("keywordsJson")), "%" + keyword.toLowerCase() + "%"));
    //         return cb.and(predicates.toArray(new Predicate[0]));
    //     };

    //     List<ServiceEntity> baseResult = repository.findAll(spec);

    //     if (lat != null && lng != null && radiusKm != null) {
    //         List<ServiceEntity> nearby = repository.findNearby(lat, lng, radiusKm);
    //         baseResult.retainAll(nearby); // Keep only entries that are both matching and nearby
    //     }

    //     return baseResult;
    // }

    public List<ServiceEntity> search(
        String q, // <-- Add this parameter at the start
        String pincode, String city, String district, String categoryId, String subcategoryId, String categoryName, String subcategoryName,
        String keyword, String title, String businessName, Double lat, Double lng, Double radiusKm) {
    Specification<ServiceEntity> spec = (root, query, cb) -> {
        List<Predicate> predicates = new ArrayList<>();

        // Global search: if 'q' is present, match it against multiple fields (OR logic)
        if (q != null && !q.isEmpty()) {
            Predicate global = cb.or(
                cb.like(cb.lower(root.get("title")), "%" + q.toLowerCase() + "%"),
                cb.like(cb.lower(root.get("business").get("name")), "%" + q.toLowerCase() + "%"),
                cb.like(cb.lower(root.get("city")), "%" + q.toLowerCase() + "%"),
                cb.like(cb.lower(root.get("district")), "%" + q.toLowerCase() + "%"),
                cb.like(cb.lower(root.get("pincode")), "%" + q.toLowerCase() + "%"),
                cb.like(cb.lower(root.get("category").get("name")), "%" + q.toLowerCase() + "%"),
                cb.like(cb.lower(root.get("subcategory").get("name")), "%" + q.toLowerCase() + "%"),
                cb.like(cb.lower(root.get("keywordsJson")), "%" + q.toLowerCase() + "%"),
                cb.like(cb.lower(root.get("address")), "%" + q.toLowerCase() + "%")
            );
            predicates.add(global);
        }

        // ...existing field-specific predicates...
        if (pincode != null && !pincode.isEmpty())
            predicates.add(cb.equal(root.get("pincode"), pincode));
        if (city != null && !city.isEmpty())
            predicates.add(cb.equal(cb.lower(root.get("city")), city.toLowerCase()));
        if (district != null && !district.isEmpty())
            predicates.add(cb.equal(cb.lower(root.get("district")), district.toLowerCase()));
        if (categoryId != null && !categoryId.isEmpty())
            predicates.add(cb.equal(root.get("category").get("id"), Long.valueOf(categoryId)));
        if (categoryName != null && !categoryName.isEmpty())
            predicates.add(cb.like(cb.lower(root.get("category").get("name")), "%" + categoryName.toLowerCase() + "%"));
        if (subcategoryId != null && !subcategoryId.isEmpty())
            predicates.add(cb.equal(root.get("subcategory").get("id"), Long.valueOf(subcategoryId)));
        if (subcategoryName != null && !subcategoryName.isEmpty())
            predicates.add(cb.like(cb.lower(root.get("subcategory").get("name")), "%" + subcategoryName.toLowerCase() + "%"));
        if (title != null && !title.isEmpty())
            predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        if (businessName != null && !businessName.isEmpty())
            predicates.add(cb.like(cb.lower(root.get("business").get("name")), "%" + businessName.toLowerCase() + "%"));
        if (keyword != null && !keyword.isEmpty())
            predicates.add(cb.like(cb.lower(root.get("keywordsJson")), "%" + keyword.toLowerCase() + "%"));

        return cb.and(predicates.toArray(new Predicate[0]));
    };

    List<ServiceEntity> baseResult = repository.findAll(spec);

    if (lat != null && lng != null && radiusKm != null) {
        List<ServiceEntity> nearby = repository.findNearby(lat, lng, radiusKm);
        baseResult.retainAll(nearby);
    }

    return baseResult;
}

    // public List<String> autocompleteTitle(String query) {
    //     return repository.autocompleteTitle(query);
    // }

    // public List<String> autocompleteBusinessName(String query) {
    //     return repository.autocompleteBusinessName(query);
    // }
//     public List<String> autocompleteField(String field, String query) {
//     switch (field) {
//         case "title":
//             return repository.autocompleteTitle(query);
//         case "city":
//             return repository.autocompleteCity(query);
//         case "district":
//             return repository.autocompleteDistrict(query);
//         case "pincode":
//             return repository.autocompletePincode(query);
//         case "address":
//             return repository.autocompleteAddress(query);
//         // Add more cases for other fields
//         default:
//             throw new IllegalArgumentException("Invalid field: " + field);
//     }
// }

public List<String> autocompleteKeyword(String query) {
    List<String> allJsons = repository.findAllKeywordsJson();
    Set<String> keywords = new HashSet<>();
    ObjectMapper mapper = new ObjectMapper();

    for (String json : allJsons) {
        if (json == null) continue;
        try {
            List<String> list = mapper.readValue(json, new TypeReference<List<String>>() {});
            for (String k : list) {
                if (k != null && k.toLowerCase().startsWith(query.toLowerCase())) {
                    keywords.add(k);
                }
            }
        } catch (Exception ignored) {}
    }
    return new ArrayList<>(keywords);
}
public List<String> autocompleteField(String field, String query) {
    switch (field) {
        case "title":
            return repository.autocompleteTitle(query);
        case "city":
            return repository.autocompleteCity(query);
        case "district":
            return repository.autocompleteDistrict(query);
        case "pincode":
            return repository.autocompletePincode(query);
        case "address":
            return repository.autocompleteAddress(query);
        case "businessName":
            return repository.autocompleteBusinessName(query);
        case "category":
        case "categoryName":
            return repository.autocompleteCategoryName(query);
        case "subcategory":
        case "subcategoryName":
            return repository.autocompleteSubcategoryName(query);
        case "keyword":
        System.out.println("Autocomplete keyword query: " + query);
            return autocompleteKeyword(query);
        default:
            throw new IllegalArgumentException("Invalid field: " + field);
    }
}
}
