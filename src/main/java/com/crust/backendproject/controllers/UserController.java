package com.crust.backendproject.controllers;

import com.crust.backendproject.dto.request.UpdatePasswordRequest;
import com.crust.backendproject.dto.request.UpdateUserRequest;
import com.crust.backendproject.dto.response.ApiResponse;
import com.crust.backendproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse> getUsers(
            @RequestParam(defaultValue = "0") @Min(0) long offset,
            @RequestParam(defaultValue = "20") @Min(1) int limit,
            HttpServletRequest httpServletRequest) {

        ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.OK.value())
                .data(userService.getAllUsers(offset, limit)).timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true).build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest,
                                                  HttpServletRequest httpServletRequest) {

        ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.OK.value())
                .data(userService.updateUser(updateUserRequest)).timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI()).isSuccessful(true).build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUser(
            @PathVariable("userId") @NotBlank(message = "User id is required") Long userId,
            HttpServletRequest httpServletRequest) {

        ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.OK.value()).data(userService.getUser(userId))
                .timeStamp(Instant.now()).path(httpServletRequest.getRequestURI()).isSuccessful(true).build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    @PostMapping("/update-password")
    public ResponseEntity<ApiResponse> updatePassword(@RequestBody @Valid UpdatePasswordRequest request,
                                                      HttpServletRequest httpServletRequest) {

        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(userService.updatePassword(request))
                .timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true).build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }
}
