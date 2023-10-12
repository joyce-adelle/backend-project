package com.crust.backendproject.controllers;

import com.crust.backendproject.dto.request.*;
import com.crust.backendproject.dto.response.ApiResponse;
import com.crust.backendproject.service.UnauthenticatedService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UnauthenticatedController {

    private final UnauthenticatedService unauthenticatedService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse> signUp(@RequestBody @Valid SignUpRequest signUpRequest,
                                              HttpServletRequest httpServletRequest) {

        ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.OK.value())
                .data(unauthenticatedService.signUp(signUpRequest)).timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI()).isSuccessful(true).build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOtp(@RequestBody @Valid OTPVerificationRequest requestOtp,
                                                 HttpServletRequest httpServletRequest) {

        ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.OK.value())
                .data(unauthenticatedService.verifyOtp(requestOtp)).timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI()).isSuccessful(true).build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponse> resendOtp(@RequestBody @Valid EmailRequest request,
                                                 HttpServletRequest httpServletRequest) {

        ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.OK.value())
                .data(unauthenticatedService.resendOtp(request)).timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI()).isSuccessful(true).build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody @Valid LoginRequest loginRequest,
                                                 HttpServletRequest httpServletRequest) {

        ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.OK.value())
                .data(unauthenticatedService.loginUser(loginRequest)).timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI()).isSuccessful(true).build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody @Valid EmailRequest request,
                                                      HttpServletRequest httpServletRequest) {

        ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.OK.value())
                .data(unauthenticatedService.forgotPassword(request)).timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI()).isSuccessful(true).build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody @Valid ResetPasswordRequest request,
                                                     HttpServletRequest httpServletRequest) {

        ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.OK.value())
                .data(unauthenticatedService.resetPassword(request)).timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI()).isSuccessful(true).build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
