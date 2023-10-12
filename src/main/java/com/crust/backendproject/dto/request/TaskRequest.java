package com.crust.backendproject.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskRequest {

    private String title;

    private String description;

}
