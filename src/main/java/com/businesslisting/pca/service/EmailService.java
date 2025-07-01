



//package com.businesslisting.pca.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class EmailService {
//
//    private final JavaMailSender mailSender;
//    @Value("${spring.mail.properties.mail.smtp.from}")
//    private String fromEmail;
//
//    public void sendWelcomeEmail(String toEmail, String name) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(fromEmail);
//        message.setTo(toEmail);
//        message.setSubject("Welcome");
//        message.setText("Welcome to Authifyv2 for you!");
//        mailSender.send(message);
//    }
//
//    public void sendResetOtpEmail(String toEmail, String otp) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(fromEmail);
//        message.setTo(toEmail);
//        message.setSubject("Password Reset OTP");
//        message.setText("Otp to reset your password is "+ otp+". Use this OTP to reset your password.");
//        mailSender.send(message);
//    }
//
//    public void sendOtpEmail(String toEmail, String otp) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(fromEmail);
//        message.setTo(toEmail);
//        message.setSubject("Account Verification OTP");
//        message.setText("your OTP for account verification is "+ otp+". Use this OTP to verify your account.");
//        mailSender.send(message);
//    }
//}


package com.businesslisting.pca.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String fromEmail;

    public void sendWelcomeEmail(String toEmail, String name, String role) {
        try {
        
            System.out.println("Sending welcome role to: " + role);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            String roleString = role.toUpperCase();
            String templateName = roleString.contains("BUSINESS_OWNER") ? "welcome_business.html" : "welcome_user.html";
            // Load and modify the HTML template
            String htmlTemplate = loadHtmlTemplate(templateName);
            String personalizedHtml = htmlTemplate.replace("${name}", name);

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Welcome to PincodeAds");
            helper.setText(personalizedHtml, true); // 'true' enables HTML

            mailSender.send(message);


        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Failed to send welcome email", e);
        }
    }

private String loadHtmlTemplate(String fileName) throws IOException {
    ClassPathResource resource = new ClassPathResource("templates/" + fileName);
    try (InputStream inputStream = resource.getInputStream()) {
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }
}


    public void sendResetOtpEmail(String toEmail, String name, String otp) {
        // SimpleMailMessage message = new SimpleMailMessage();
        // message.setFrom(fromEmail);
        // message.setTo(toEmail);
        // message.setSubject("Password Reset OTP");
        // message.setText("Otp to reset your password is "+ otp+". Use this OTP to reset your password.");
        // mailSender.send(message);
         try {
        
            System.out.println("Sending reset otp to: " + toEmail );
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Load and modify the HTML template
            String htmlTemplate = loadHtmlTemplate("reset_password.html");
            String personalizedHtml = htmlTemplate.replace("${name}", name).replace("${otp}", otp);

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Reset Password OTP");
            helper.setText(personalizedHtml, true); // 'true' enables HTML

            mailSender.send(message);
            System.out.println("Reset OTP email sent successfully to: " + toEmail);

        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Failed to send password reset otp email", e);
        }
    }

    public void sendOtpEmail(String toEmail, String otp,String role ,String name ) {
        // System.out.println("Sending OTP email to: " + toEmail);
        // SimpleMailMessage message = new SimpleMailMessage();
        // message.setFrom(fromEmail);
        // message.setTo(toEmail);
        // message.setSubject("Account Verification OTP");
        // message.setText("your OTP for account verification is "+ otp+". Use this OTP to verify your account.");
        // mailSender.send(message);
          try {
        
            System.out.println("Sending verify otp role to: " + role);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            String roleString = role.toUpperCase();
            String templateName = roleString.contains("BUSINESS_OWNER") ? "business_reg_otp.html" : "user_reg_otp.html";
            // Load and modify the HTML template
            String htmlTemplate = loadHtmlTemplate(templateName);
            String personalizedHtml = htmlTemplate.replace("${name}", name).replace("${otp}", otp);

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Account Verification OTP");
            helper.setText(personalizedHtml, true); // 'true' enables HTML

            mailSender.send(message);
            System.out.println("OTP email sent successfully to: " + toEmail);


        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Failed to send verify otp email", e);
        }
    }
}
