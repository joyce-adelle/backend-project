package com.crust.backendproject.service;

import com.crust.backendproject.dto.request.*;
import com.crust.backendproject.dto.response.LoginResponse;
import com.crust.backendproject.dto.response.MessageResponse;

public interface UnauthenticatedService {

	MessageResponse signUp(SignUpRequest signUpRequest);

	MessageResponse verifyOtp(OTPVerificationRequest requestOtp);

	LoginResponse loginUser(LoginRequest loginRequest);

	MessageResponse resendOtp(EmailRequest request);

	MessageResponse resetPassword(ResetPasswordRequest request);

	MessageResponse forgotPassword(EmailRequest request);

}
