

package com.otplogin.otplogin.service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.otplogin.otplogin.model.OtpData;

@Service
public class OtpService {

    private Map<String, OtpData> otpStore = new HashMap<>();

    public String generateOtp(String email) {

        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);

        otpStore.put(email, new OtpData(email, otp, expiry));
        return otp;
    }

    public boolean verifyOtp(String email, String otp) {

        OtpData data = otpStore.get(email);

        if (data == null) return false;
        if (data.getExpiryTime().isBefore(LocalDateTime.now())) return false;

        return data.getOtp().equals(otp);
    }
}
