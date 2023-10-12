package com.crust.backendproject.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResetPasswordRequest {

    @Email(message="Email invalid")
    @NotBlank(message="Email is required")
    private String email;

    @NotBlank(message="New Password is required")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z\\d@#$%^&+=]{8,15}$", message = "Password not strong enough")
    private String newPassword;

    @Pattern(regexp = "^\\d{6}$", message = "Invalid otp")
    @NotBlank(message="OTP is required")
    private String otp;

}
