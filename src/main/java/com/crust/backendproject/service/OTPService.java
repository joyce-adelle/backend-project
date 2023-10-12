package com.crust.backendproject.service;

import com.crust.backendproject.models.OTP;

import java.util.Optional;

public interface OTPService {

    void saveVerificationOtp(OTP otp);

    Optional<OTP> getVerificationOtp(String otp, String email);

    void deleteExpiredOtp();

    void setConfirmationAt(String email);

}
