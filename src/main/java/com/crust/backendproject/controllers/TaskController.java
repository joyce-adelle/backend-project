package com.crust.backendproject.controllers;

import com.crust.backendproject.dto.request.CreateTaskRequest;
import com.crust.backendproject.dto.request.UpdateTaskRequest;
import com.crust.backendproject.dto.response.ApiResponse;
import com.crust.backendproject.service.TaskService;
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
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<ApiResponse> createTask(@RequestBody @Valid CreateTaskRequest taskRequest,
                                                  HttpServletRequest httpServletRequest) {

        ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.OK.value())
                .data(taskService.createTask(taskRequest)).timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI()).isSuccessful(true).build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getTasks(
            @RequestParam(defaultValue = "0") @Min(0) long offset,
            @RequestParam(defaultValue = "20") @Min(1) int limit,
            HttpServletRequest httpServletRequest) {

        ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.OK.value())
                .data(taskService.getAllTasks(offset, limit)).timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true).build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResponse> updateTask(@PathVariable("taskId") @NotBlank(message = "Task id is required") Long taskId,
                                                  @RequestBody @Valid UpdateTaskRequest taskRequest,
                                                  HttpServletRequest httpServletRequest) {

        ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.OK.value())
                .data(taskService.updateTask(taskId, taskRequest)).timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI()).isSuccessful(true).build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse> getTask(
            @PathVariable("taskId") @NotBlank(message = "Task id is required") Long taskId,
            HttpServletRequest httpServletRequest) {

        ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.OK.value()).data(taskService.getTask(taskId))
                .timeStamp(Instant.now()).path(httpServletRequest.getRequestURI()).isSuccessful(true).build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable("taskId") @NotBlank(message = "Task id is required") Long taskId,
                                                  HttpServletRequest httpServletRequest) {

        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(taskService.deleteTask(taskId))
                .timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true).build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

}
