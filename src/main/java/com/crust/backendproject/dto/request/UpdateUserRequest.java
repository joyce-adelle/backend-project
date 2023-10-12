package com.crust.backendproject.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateUserRequest {

	@NotBlank(message="Name is required")
	private String name;

}
