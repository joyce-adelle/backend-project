package com.crust.backendproject.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateTaskRequest {

    private String title;

    private String description;

}
