package com.crust.backendproject.service;

import com.crust.backendproject.dto.request.UpdatePasswordRequest;
import com.crust.backendproject.dto.request.UpdateUserRequest;
import com.crust.backendproject.dto.response.MessageResponse;
import com.crust.backendproject.models.User;

import java.util.List;

public interface UserService {

    User updateUser(UpdateUserRequest updateUserRequest);

    User getUser(Long userId);

    List<User> getAllUsers(long offset, int limit);

    MessageResponse updatePassword(UpdatePasswordRequest request);

}
