package com.crust.backendproject.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailRequest {
    @Email(message = "Input a valid email")
    @NotBlank(message = "Email is required")
    private String email;
}
