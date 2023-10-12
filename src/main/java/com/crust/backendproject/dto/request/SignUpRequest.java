package com.crust.backendproject.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignUpRequest {

	@Email(message="User email invalid")
	@NotBlank(message="Email is required")
	private String email;

	@NotBlank(message="Password is required")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z\\d@#$%^&+=]{8,15}$", message = "Password not strong enough")
	private String password;

	@NotBlank(message="Name is required")
	private String name;

}
