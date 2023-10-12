package com.crust.backendproject.service;

import com.crust.backendproject.dto.request.UpdatePasswordRequest;
import com.crust.backendproject.dto.request.UpdateTaskRequest;
import com.crust.backendproject.dto.request.UpdateUserRequest;
import com.crust.backendproject.dto.response.MessageResponse;
import com.crust.backendproject.models.Task;
import com.crust.backendproject.models.User;

import java.util.List;

public interface TaskService {

    Task updateTask(UpdateTaskRequest updateTaskRequest);

    Task getTask(Long taskId);

    List<Task> getAllTasks(long offset, int limit);

    MessageResponse deleteTask(Long taskId);

}
