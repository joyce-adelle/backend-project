package com.crust.backendproject.dto.response;

import com.crust.backendproject.models.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private User user;
    private String token;

}
