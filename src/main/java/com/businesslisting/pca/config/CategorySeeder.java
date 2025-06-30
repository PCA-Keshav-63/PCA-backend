// package com.businesslisting.pca.config;


// import com.businesslisting.pca.model.Category;
// import com.businesslisting.pca.repository.CategoryRepository;
// import lombok.RequiredArgsConstructor;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import java.util.Arrays;
// import java.util.List;

// @Component
// @RequiredArgsConstructor
// public class CategorySeeder implements CommandLineRunner {

//     private final CategoryRepository categoryRepository;

//     @Override
//     public void run(String... args) throws Exception {
//         List<String> categoryNames = Arrays.asList(
//                 "AC Service", "Astrologers", "Body Massage Centers", "Beauty Spa",
//                 "Car Hire", "Caterers", "Chartered Accountant", "Computer Training Institutes",
//                 "Courier Services", "Computer & Laptop Repair & Services", "Car Repair & Services",
//                 "Dermatologists", "Dentists", "Electricians", "Event Organizer", "Real Estate",
//                 "Fabricators", "Furniture Repair Services", "Hospitals", "House keeping Services",
//                 "Hobbies", "Interior Designers", "Internet Website Designers", "Jewellery Showrooms",
//                 "Nursing Services", "Lawyers", "Transporters", "Photographers",
//                 "Printing & Publishing Services", "Placement Services", "Pest Control Services",
//                 "Painting Contractors", "Packers & Movers", "Scrap Dealers", "Scrap Buyers",
//                 "Registration Consultants", "Security System", "Coaching", "Vocational training"
//         );

//         for (String name : categoryNames) {
//             if (!categoryRepository.existsByName(name)) {
//                 categoryRepository.save(new Category(name, null)); // icon_url is null for now
//             }
//         }

//         System.out.println("✅ Categories seeded successfully");
//     }
// }


// package com.businesslisting.pca.config;

// import com.businesslisting.pca.model.Category;
// import com.businesslisting.pca.repository.CategoryRepository;
// import lombok.RequiredArgsConstructor;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import java.util.Map;

// @Component
// @RequiredArgsConstructor
// public class CategorySeeder implements CommandLineRunner {

//     private final CategoryRepository categoryRepository;

//     @Override
//     public void run(String... args) {
//         Map<String, String> categoryIconMap = Map.ofEntries(
//             Map.entry("Astrologers", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/astrologers.svg?w=64&q=75"),
//             Map.entry("AC Service", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/acservice.svg?w=64&q=75"),
//             Map.entry("Courier Services", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/internationalcourierservices.svg?w=64&q=75"),
//             Map.entry("Dentists", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/dentists.svg?w=64&q=75"),
//             Map.entry("Fabricators", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/constructionrealestate.svg?w=64&q=75"),
//             Map.entry("Hobbies", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/yogaclasses.svg?w=64&q=75"),
//             Map.entry("Lawyers", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/lawyers.svg?w=64&q=75"),
//             Map.entry("Printing & Publishing Services", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/flexprintingservices.svg?w=64&q=75"),
//             Map.entry("Packers & Movers", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/moversforautomobile.svg?w=64&q=75"),
//             Map.entry("Security System", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/securitycctv.svg?w=64&q=75"),
//             Map.entry("Caterers", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/caterers.svg?w=64&q=75"),
//             Map.entry("Computer & Laptop Repair & Services", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/computerrepairs.svg?w=64&q=75"),
//             Map.entry("Electricians", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/electricians.svg?w=64&q=75"),
//             Map.entry("Furniture Repair Services", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/furniture.svg?w=64&q=75"),
//             Map.entry("Interior Designers", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/interiordesigners.svg?w=64&q=75"),
//             Map.entry("Transporters", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/transporters.svg?w=64&q=75"),
//             Map.entry("Placement Services", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/jobs.svg?w=64&q=75"),
//             Map.entry("Scrap Dealers", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/scrap-dealers.svg?w=64&q=75"),
//             Map.entry("Coaching", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/tutorials.svg?w=64&q=75"),
//             Map.entry("Body Massage Centers", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/masseur.svg?w=64&q=75"),
//             Map.entry("Chartered Accountant", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/charteredaccountants.svg?w=64&q=75"),
//             Map.entry("Car Repair & Services", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/carrepairservices.svg?w=64&q=75"),
//             Map.entry("Event Organizer", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/eventorganizers.svg?w=64&q=75"),
//             Map.entry("Hospitals", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/hotels.svg?w=64&q=75"), // 🔄 mislabeled
//             Map.entry("Internet Website Designers", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/internet.svg?w=64&q=75"),
//             Map.entry("Photographers", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/photographers.svg?w=64&q=75"),
//             Map.entry("Pest Control Services", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/pestcontrol.svg?w=64&q=75"),
//             Map.entry("Vocational training", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/traininginstitutes.svg?w=64&q=75"),
//             Map.entry("Beauty Spa", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/beauty.svg?w=64&q=75"),
//             Map.entry("Computer Training Institutes", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/computertraininginstitutes.svg?w=64&q=75"),
//             Map.entry("Dermatologists", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/dermatologists.svg?w=64&q=75"),
//             Map.entry("Real Estate", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/realestate.svg?w=64&q=75"),
//             Map.entry("House keeping Services", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/housekeeping.svg?w=64&q=75"),
//             Map.entry("Jewellery Showrooms", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/jewellery.svg?w=64&q=75"),
//             Map.entry("Nursing Services", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/nursebureaus.svg?w=64&q=75"),
//             Map.entry("Painting Contractors", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/paintingcontractors.svg?w=64&q=75"),
//             Map.entry("Registration Consultants", "https://akam.cdn.jdmagicbox.com/images/icons/newwap/newhotkey/registrationconsultants.svg?w=64&q=75")
//         );

//         categoryIconMap.forEach((name, iconUrl) -> {
//             if (!categoryRepository.existsByName(name)) {
//                 categoryRepository.save(new Category(name, iconUrl));
//             }
//         });

//         System.out.println("✅ Categories with icons seeded.");
//     }
// }
