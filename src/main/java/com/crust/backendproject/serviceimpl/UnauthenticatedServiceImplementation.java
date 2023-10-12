package com.crust.backendproject.serviceimpl;

import com.crust.backendproject.dto.request.*;
import com.crust.backendproject.dto.response.LoginResponse;
import com.crust.backendproject.dto.response.MessageResponse;
import com.crust.backendproject.email.EmailSender;
import com.crust.backendproject.exception.AppException;
import com.crust.backendproject.models.OTP;
import com.crust.backendproject.models.User;
import com.crust.backendproject.repositories.UserRepository;
import com.crust.backendproject.security.JwtService;
import com.crust.backendproject.service.OTPService;
import com.crust.backendproject.service.UnauthenticatedService;
import com.crust.backendproject.util.Errors;
import com.crust.backendproject.util.Roles;
import com.crust.backendproject.util.UtilClass;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnauthenticatedServiceImplementation implements UnauthenticatedService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OTPService otpService;
    private final EmailSender emailSender;
    private final JwtService jwtService;

    @Override
    public MessageResponse signUp(SignUpRequest signUpRequest) {

        boolean checkEmail = userRepository.existsByEmailIgnoreCase(signUpRequest.getEmail());
        if (checkEmail)
            throw new AppException(Errors.DUPLICATE_USER_EMAIL);

        User user = User.builder().email(signUpRequest.getEmail().toLowerCase())
                .password(passwordEncoder.encode(signUpRequest.getPassword())).name(signUpRequest.getName())
                .role(Roles.USER).isVerified(false).build();

        try {

            userRepository.save(user);
            String token = UtilClass.generateOtp();
            OTP verificationOtp = OTP.builder().otp(token).email(user.getEmail().toLowerCase())
                    .expiredAt(Instant.now().plus(10, ChronoUnit.MINUTES)).build();
            otpService.saveVerificationOtp(verificationOtp);

            emailSender.send(signUpRequest.getEmail(), UtilClass.buildEmail(signUpRequest.getName(), token));

        } catch (AppException e) {
            log.error(e.getMessage(), e.getCause());
            throw e;
        } catch (RuntimeException e) {
            log.error("Error sending email: ", e.getCause());
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            throw new AppException(Errors.ERROR_OCCURRED_PERFORMING_ACTION);
        }

        return MessageResponse.builder().message("Signup Successful").build();

    }

    @Override
    public MessageResponse verifyOtp(OTPVerificationRequest otpRequest) {

        OTP otp = otpService.getVerificationOtp(otpRequest.getOtp(), otpRequest.getEmail())
                .orElseThrow(() -> new AppException(Errors.INVALID_OTP));

        var user = userRepository.findByEmailIgnoreCase(otpRequest.getEmail().toLowerCase())
                .orElseThrow(() -> new AppException(Errors.USER_NOT_FOUND));

        if (otp.getExpiredAt().isBefore(Instant.now()))
            throw new AppException(Errors.OTP_EXPIRED);

        if (!UtilClass.isNull(otp.getConfirmedAt()))
            throw new AppException(Errors.INVALID_OTP);

        if (!UtilClass.isNull(user.getIsVerified()) && user.getIsVerified())
            throw new AppException(Errors.USER_ALREADY_VERIFIED);

        user.setIsVerified(true);
        user.setVerifiedAt(Instant.now());

        userRepository.save(user);
        otpService.setConfirmationAt(otp.getEmail());

        return MessageResponse.builder().message("Verification Successful").build();

    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {

        User user = userRepository.findByEmailIgnoreCase(loginRequest.getEmail())
                .orElseThrow(() -> new AppException(Errors.USER_NOT_FOUND));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new AppException(Errors.INVALID_LOGIN_DETAILS);

        if (!UtilClass.isNull(user.getIsVerified()) && !user.getIsVerified()) {
            log.warn("Email has not been verified {}", user.getEmail());
            throw new AppException(Errors.USER_NOT_VERIFIED);
        }

        try {

            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return LoginResponse.builder().user(user)
                    .token(jwtService.generateToken(authentication))
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            throw new AppException(Errors.ERROR_OCCURRED_PERFORMING_ACTION);
        }

    }

    @Override
    public MessageResponse resendOtp(EmailRequest request) {

        var user = userRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new AppException(Errors.USER_NOT_FOUND));

        if (!UtilClass.isNull(user.getIsVerified()) && user.getIsVerified())
            throw new AppException(Errors.USER_ALREADY_VERIFIED);

        try {

            String token = UtilClass.generateOtp();
            OTP verificationOtp = OTP.builder().email(user.getEmail().toLowerCase()).otp(token)
                    .expiredAt(Instant.now().plus(10, ChronoUnit.MINUTES)).build();
            otpService.saveVerificationOtp(verificationOtp);

            emailSender.send(request.getEmail(), UtilClass.buildEmail(user.getName(), token));

        } catch (AppException e) {
            log.error(e.getMessage(), e.getCause());
            throw e;
        } catch (RuntimeException e) {
            log.error("Error sending email: ", e.getCause());
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            throw new AppException(Errors.ERROR_OCCURRED_PERFORMING_ACTION);
        }

        return MessageResponse.builder().message("Email Successfully resent").build();
    }

    @Override
    public MessageResponse forgotPassword(EmailRequest request) {

        var user = userRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new AppException(Errors.USER_NOT_FOUND));

        try {

            String token = UtilClass.generateOtp();
            OTP verificationOtp = OTP.builder().email(user.getEmail().toLowerCase()).otp(token)
                    .expiredAt(Instant.now().plus(10, ChronoUnit.MINUTES)).build();
            otpService.saveVerificationOtp(verificationOtp);

            emailSender.send(request.getEmail(), UtilClass.buildEmail(user.getName(), token));

        } catch (AppException e) {
            log.error(e.getMessage(), e.getCause());
            throw e;
        } catch (RuntimeException e) {
            log.error("Error sending email: ", e.getCause());
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            throw new AppException(Errors.ERROR_OCCURRED_PERFORMING_ACTION);
        }

        return MessageResponse.builder().message("Otp Successfully sent").build();
    }

    @Override
    public MessageResponse resetPassword(ResetPasswordRequest request) {

        OTP otp = otpService.getVerificationOtp(request.getOtp(), request.getEmail())
                .orElseThrow(() -> new AppException(Errors.INVALID_OTP));

        if (otp.getExpiredAt().isBefore(Instant.now()))
            throw new AppException(Errors.OTP_EXPIRED);

        if (!UtilClass.isNull(otp.getConfirmedAt()))
            throw new AppException(Errors.INVALID_OTP);

        try {

            userRepository.updatePasswordByEmail(passwordEncoder.encode(request.getNewPassword()), otp.getEmail());
            otpService.setConfirmationAt(otp.getEmail());

        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            throw new AppException(Errors.ERROR_OCCURRED_PERFORMING_ACTION);
        }

        return MessageResponse.builder().message("Password reset successfully").build();
    }

}
