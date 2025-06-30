package com.businesslisting.pca.io;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {

    @NotBlank(message = "New Password is Required")
    private String newPassword;
    @NotBlank(message = "OTP is Required")
    private String otp;
    @NotBlank(message = "Email is Required")
    private String email;


}
