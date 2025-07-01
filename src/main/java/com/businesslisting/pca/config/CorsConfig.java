package com.businesslisting.pca.config;


   
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply CORS to all endpoints
                .allowedOrigins("http://localhost:3000", "http://localhost:3001", "http://localhost:8081", "https://pincodeads.vercel.app/") // Replace with your frontend's actual URL(s)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                .allowedHeaders("*") // Allow all headers from the client
                .allowCredentials(true) // Allow sending cookies, authorization headers, etc.
                .maxAge(3600); // Cache preflight requests for 1 hour (optional)
    }
}

