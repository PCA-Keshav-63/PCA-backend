package com.businesslisting.pca.service;

import com.businesslisting.pca.io.ProfileRequest;
import com.businesslisting.pca.io.ProfileResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProfileService {

    ProfileResponse createProfile(ProfileRequest request);

    ProfileResponse getProfile(String email);

    void sendResetOtp(String email);

    void resetPassword(String email, String otp, String newPassword);

    //for verify email
    void sendOtp(String email) ;
    //uss sentopt hua hai usko verify karne ke liye
    void verifyOtp(String email, String otp);

    String getLoggedInUserId(String email);
}
