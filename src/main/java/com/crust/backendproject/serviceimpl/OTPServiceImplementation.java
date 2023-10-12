package com.crust.backendproject.serviceimpl;

import com.crust.backendproject.models.OTP;
import com.crust.backendproject.repositories.OTPRepository;
import com.crust.backendproject.service.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OTPServiceImplementation implements OTPService {

    private final OTPRepository otpRepository;

    @Override
    public void saveVerificationOtp(OTP otp) {
        otpRepository.save(otp);
    }

    @Override
    public Optional<OTP> getVerificationOtp(String otp, String email) {
        return otpRepository.findByOtpAndEmail(otp, email);
    }

    @Override
    public void deleteExpiredOtp() {
        otpRepository.deleteOtpByExpiredAtBefore(Instant.now());
    }

    @Override
    public void setConfirmationAt(String email) {
        otpRepository.confirmedAt(Instant.now(), email);
    }

}
