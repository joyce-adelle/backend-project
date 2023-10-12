package com.crust.backendproject.service;

import com.crust.backendproject.dto.request.TaskRequest;
import com.crust.backendproject.dto.response.MessageResponse;
import com.crust.backendproject.models.Task;

import java.util.List;

public interface TaskService {

    Task createTask(TaskRequest taskRequest);

    Task updateTask(Long taskId, TaskRequest updateTaskRequest);

    Task getTask(Long taskId);

    List<Task> getAllTasks(long offset, int limit);

    MessageResponse deleteTask(Long taskId);

}
