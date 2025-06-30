// package com.businesslisting.pca.service;

// import com.businesslisting.pca.entity.Role;
// import com.businesslisting.pca.entity.UserEntity;
// import com.businesslisting.pca.io.ProfileRequest;
// import com.businesslisting.pca.io.ProfileResponse;
// import com.businesslisting.pca.repository.UserRepository;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.mail.MailSender;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;
// import org.springframework.web.server.ResponseStatusException;

// import java.sql.SQLOutput;
// import java.util.UUID;
// import java.util.concurrent.ThreadLocalRandom;

// @Service
// @RequiredArgsConstructor
// public class ProfileServiceImpl implements ProfileService {

//     private final UserRepository userRepository;
//     private final PasswordEncoder passwordEncoder;
//     private final EmailService emailService;


// @Override
// public ProfileResponse createProfile(ProfileRequest request) {
//     System.out.println("📥 Inside Create Profile");

//     if (userRepository.existsByEmail(request.getEmail())) {
//         System.out.println("❌ Email already exists: " + request.getEmail());
//         throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
//     }

//     System.out.println("✅ Creating new profile for: " + request.getEmail());
//     UserEntity newProfile = convertToUserEntity(request);
//     newProfile = userRepository.save(newProfile);
//     return convertToProfileResponse(newProfile);
// }


//     @Override
//     public ProfileResponse getProfile(String email) {
//         UserEntity existinguser = userRepository.findByEmail(email)
//         .orElseThrow(()->new UsernameNotFoundException("User Not Found "+ email));
//         return convertToProfileResponse(existinguser);
//     }

//     @Override
//     public void sendResetOtp(String email) {
//         UserEntity existingEntity = userRepository.findByEmail(email)
//                 .orElseThrow(()->new UsernameNotFoundException("User Not Found "+ email));

//         //Generate 6 digit Otp
//         String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000,1000000));

//         //Calculate the expiry time(current time + 5 minutes in millisecond)
//         long expiryTime = System.currentTimeMillis()+(15*60*1000);

//         //update the profile entity
//         existingEntity.setResetOtp(otp);
//         existingEntity.setResetOtpExpireAt(expiryTime);

//         //save into the database
//         userRepository.save(existingEntity);

//         try{
//             emailService.sendResetOtpEmail(existingEntity.getEmail(), otp);
//         }catch(Exception e){
//             throw new RuntimeException("Unable to send reset OTP");
//         }
//     }

//     @Override
//     public void resetPassword(String email, String otp, String newpassword) {
//         UserEntity existingUser = userRepository.findByEmail(email)
//                 .orElseThrow(()->new UsernameNotFoundException("User Not Found "+ email));

//         if (existingUser.getResetOtp() == null || !existingUser.getResetOtp().equals(otp)) {
//             throw new RuntimeException("Reset OTP is not valid");
//         }

//         if (existingUser.getResetOtpExpireAt()< System.currentTimeMillis()) {
//             throw new RuntimeException("Reset OTP expired");
//         }

//         existingUser.setPassword(passwordEncoder.encode(newpassword));
//         existingUser.setResetOtp(null);
//         existingUser.setResetOtpExpireAt(0l);

//         userRepository.save(existingUser);

//     }

// @Override
// public void sendOtp(String email) {
//     System.out.println("📥 Inside sendOtp for email: " + email);
    
//     UserEntity existingUser = userRepository.findByEmail(email)
//         .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + email));

//     if (existingUser.getIsAccountVerified() != null && existingUser.getIsAccountVerified()) {
//         throw new RuntimeException("Account is already verified");
//     }

//     // Generate OTP
//     String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
//     long expiryTime = System.currentTimeMillis() + (15 * 60 * 1000);

//     // Update user
//     existingUser.setVerifyOtp(otp);
//     existingUser.setVerifyOtpExpireAt(expiryTime);

//     userRepository.saveAndFlush(existingUser);

//     System.out.println("✅ OTP Generated: " + otp + " for " + existingUser.getEmail());

//     try {
//         emailService.sendOtpEmail(existingUser.getEmail(), otp);
//         System.out.println("📧 OTP email sent successfully");
//     } catch (Exception e) {
//         e.printStackTrace();
//         throw new RuntimeException("Unable to send OTP: " + e.getMessage());
//     }
// }

// @Override
// public void verifyOtp(String email, String otp) {
//     System.out.println("📥 Inside verifyOtp for email: " + email);

//     UserEntity existingUser = userRepository.findByEmail(email)
//         .orElseThrow(() -> new UsernameNotFoundException("User Not Found " + email));

//     if (existingUser.getVerifyOtp() == null || !existingUser.getVerifyOtp().equals(otp)) {
//         throw new RuntimeException("❌ Invalid OTP");
//     }

//     if (existingUser.getVerifyOtpExpireAt() < System.currentTimeMillis()) {
//         throw new RuntimeException("❌ OTP expired");
//     }

//     existingUser.setIsAccountVerified(true);
//     existingUser.setVerifyOtp(null);
//     existingUser.setVerifyOtpExpireAt(0); // ✅ clear expiry
//     existingUser.setResetOtp(null);       // ✅ optional cleanup
//     existingUser.setResetOtpExpireAt(0);

//     userRepository.save(existingUser);

//     System.out.println("✅ OTP verified. User activated: " + email);
// }

//     @Override
//     public String getLoggedInUserId(String email) {
//         UserEntity existingUser = userRepository.findByEmail(email)
//                 .orElseThrow(() -> new UsernameNotFoundException("User Not Found "+ email));
//                 return existingUser.getUserId();
//     }

//     private ProfileResponse convertToProfileResponse(UserEntity newProfile) {
//         return ProfileResponse.builder()
//                 .name(newProfile.getName())
//                 .email(newProfile.getEmail())
//                 .userId(newProfile.getUserId())
//                 .isAccountVerified(newProfile.getIsAccountVerified())
//                 .build();
//     }

//     private UserEntity convertToUserEntity(ProfileRequest request) {
//         return UserEntity.builder()
//                 .email(request.getEmail())
//                 .userId(UUID.randomUUID().toString())
//                 .name(request.getName())
//                 .password(passwordEncoder.encode(request.getPassword()))
//                 .role(request.getRole() != null ? request.getRole() : Role.USER)  // ✅ Use requested role or fallback to USER
//                 .isAccountVerified(false)
//                 .resetOtpExpireAt(0)
//                 .verifyOtp(null)
//                 .verifyOtpExpireAt(0)
//                 .build();
//     }
// }

package com.businesslisting.pca.service.impl;

// import com.businesslisting.pca.model.Role;
import com.businesslisting.pca.model.UserEntity;
import com.businesslisting.pca.io.ProfileRequest;
import com.businesslisting.pca.io.ProfileResponse;
import com.businesslisting.pca.repository.UserRepository;
import com.businesslisting.pca.service.EmailService;
import com.businesslisting.pca.service.ProfileService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public ProfileResponse createProfile(ProfileRequest request) {
        System.out.println("📥 Inside Create Profile");
        System.out.println("📥 Register request received insdie kfaskjdfh: " + request);
        if (userRepository.existsByEmail(request.getEmail())) {
            System.out.println("❌ Email already exists: " + request.getEmail());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        System.out.println("✅ Creating new profile for: " + request.getEmail());
        UserEntity newProfile = convertToUserEntity(request);

        try {
            newProfile = userRepository.save(newProfile);
            System.out.println("✅ Profile saved: " + newProfile.getEmail()+ newProfile.getRole());
            return convertToProfileResponse(newProfile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create user profile");
        }
    }

    @Override
    public ProfileResponse getProfile(String email) {
        System.out.println("📤 Fetching profile for: " + email);
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + email));
        return convertToProfileResponse(user);
    }
@Override
public void sendResetOtp(String email) {
    System.out.println("🔁 Sending reset OTP to: " + email);

    UserEntity user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + email));

    String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
    long expiryTime = System.currentTimeMillis() + (15 * 60 * 1000);

    user.setResetOtp(otp);
    user.setResetOtpExpireAt(expiryTime);

    String name = user.getName(); // Fetch name from DB

    try {
        userRepository.save(user);
        emailService.sendResetOtpEmail(email, name, otp); // Pass name to email service
        System.out.println("✅ Reset OTP sent to " + email);
    } catch (Exception e) {
        e.printStackTrace();
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send reset OTP");
    }
}

    @Override
    public void resetPassword(String email, String otp, String newPassword) {
        System.out.println("🔒 Resetting password for: " + email);

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (user.getResetOtp() == null || !user.getResetOtp().equals(otp)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reset OTP is not valid");
        }

        if (user.getResetOtpExpireAt() < System.currentTimeMillis()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reset OTP has expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetOtp(null);
        user.setResetOtpExpireAt(0L);

        try {
            userRepository.save(user);
            System.out.println("✅ Password updated for: " + email);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update password");
        }
    }

@Override
public void sendOtp(String email) {
    System.out.println("📨 Sending verification OTP to: " + email);

    UserEntity user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + email));

    if (Boolean.TRUE.equals(user.getIsAccountVerified())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account already verified");
    }

    String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
    long expiryTime = System.currentTimeMillis() + (15 * 60 * 1000);

    user.setVerifyOtp(otp);
    user.setVerifyOtpExpireAt(expiryTime);

    String name = user.getName();
    String role = user.getRole();

    try {
        userRepository.saveAndFlush(user);
        emailService.sendOtpEmail(email, otp, role, name);
        System.out.println("✅ OTP email sent to: " + email);
    } catch (Exception e) {
        e.printStackTrace();
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send OTP");
    }
}

    @Override
    public void verifyOtp(String email, String otp) {
        System.out.println("🔐 Verifying OTP for: " + email);

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (user.getVerifyOtp() == null || !user.getVerifyOtp().equals(otp)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP");
        }

        if (user.getVerifyOtpExpireAt() < System.currentTimeMillis()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP has expired");
        }

        user.setIsAccountVerified(true);
        user.setVerifyOtp(null);
        user.setVerifyOtpExpireAt(0L);
        user.setResetOtp(null);
        user.setResetOtpExpireAt(0L);

        try {
            userRepository.save(user);
            System.out.println("✅ Account verified for: " + email);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to verify account");
        }
    }

    @Override
    public String getLoggedInUserId(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return user.getUserId();
    }

    // Converts Entity to Response DTO
    private ProfileResponse convertToProfileResponse(UserEntity user) {
        return ProfileResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole() != null ? user.getRole() : "USER") // Fallback to USER if role is null
                .isAccountVerified(user.getIsAccountVerified())
                .build();
    }

    // Converts Request DTO to Entity
    private UserEntity convertToUserEntity(ProfileRequest request) {
        return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : "USER")
                .isAccountVerified(false)
                .verifyOtp(null)
                .verifyOtpExpireAt(0L)
                .resetOtp(null)
                .resetOtpExpireAt(0L)
                .build();
    }
}
// This code is a complete implementation of the ProfileService interface, handling user profile creation, retrieval, OTP management, and password reset functionalities.
// It includes error handling and logging for better traceability. The service uses a UserRepository for