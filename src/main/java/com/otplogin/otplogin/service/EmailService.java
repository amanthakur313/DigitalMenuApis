
package com.otplogin.otplogin.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtp(String userEmail, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail); // USER ki Gmail
        message.setSubject("OTP Verification Code");
        message.setText(
                "Your OTP is: " + otp +
                "\nThis OTP is valid for 5 minutes."
        );

        mailSender.send(message);
    }
}
