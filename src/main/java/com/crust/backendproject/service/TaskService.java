package com.crust.backendproject.service;

import com.crust.backendproject.dto.request.CreateTaskRequest;
import com.crust.backendproject.dto.request.UpdateTaskRequest;
import com.crust.backendproject.dto.response.MessageResponse;
import com.crust.backendproject.models.Task;

import java.util.List;

public interface TaskService {

    Task createTask(CreateTaskRequest taskRequest);

    Task updateTask(Long taskId, UpdateTaskRequest updateTaskRequest);

    Task getTask(Long taskId);

    List<Task> getAllTasks(long offset, int limit);

    MessageResponse deleteTask(Long taskId);

}
