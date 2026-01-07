package com.otplogin.otplogin.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otplogin.otplogin.service.EmailService;
import com.otplogin.otplogin.service.OtpService;

@RestController
@RequestMapping("/api/auth")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    // SEND OTP
    @PostMapping("/send-otp")
    public Map<String, Object> sendOtp(@RequestParam String email) {

        String otp = otpService.generateOtp(email);
        emailService.sendOtp(email, otp);

        Map<String, Object> res = new HashMap<>();
        res.put("status", true);
        res.put("message", "OTP sent to " + email);

        return res;
    }

    // VERIFY OTP & LOGIN
    @PostMapping("/verify-otp")
    public Map<String, Object> verifyOtp(
            @RequestParam String email,
            @RequestParam String otp) {

        boolean verified = otpService.verifyOtp(email, otp);

        Map<String, Object> res = new HashMap<>();
        if (verified) {
            res.put("status", true);
            res.put("message", "Login successful");
        } else {
            res.put("status", false);
            res.put("message", "Invalid or expired OTP");
        }

        return res;
    }
}
