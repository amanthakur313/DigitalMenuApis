
package com.otplogin.otplogin.model;
import java.time.LocalDateTime;

public class OtpData {

    private String email;
    private String otp;
    private LocalDateTime expiryTime;

    public OtpData(String email, String otp, LocalDateTime expiryTime) {
        this.email = email;
        this.otp = otp;
        this.expiryTime = expiryTime;
    }

    public String getEmail() {
        return email;
    }

    public String getOtp() {
        return otp;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }
}
