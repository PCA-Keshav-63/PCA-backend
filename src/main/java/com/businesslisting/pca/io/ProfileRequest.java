package com.businesslisting.pca.io;

// import com.businesslisting.pca.model.Role;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProfileRequest {

    @NotBlank(message = "Name cannot be empty")
    private String name;
    
    @Email(message = "Enter a valid email address")
    @NotNull(message = "Email cannot be empty")
    private String email;

    @Size(min = 6, message = "Password must be atleast 6 characters")
    private String password;

    // @Builder.Default
    private String role = "USER"; // Default to USER if not provided

}
