package com.crust.backendproject.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateTaskRequest {

    @NotBlank(message="Title is required")
    private String title;

    @NotBlank(message="Description is required")
    private String description;

}
