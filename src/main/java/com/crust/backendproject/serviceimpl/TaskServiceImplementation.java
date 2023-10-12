package com.crust.backendproject.serviceimpl;

import com.crust.backendproject.audit.AuditAware;
import com.crust.backendproject.dto.request.TaskRequest;
import com.crust.backendproject.dto.response.MessageResponse;
import com.crust.backendproject.exception.AppException;
import com.crust.backendproject.models.Task;
import com.crust.backendproject.repositories.TaskRepository;
import com.crust.backendproject.service.TaskService;
import com.crust.backendproject.util.Errors;
import com.crust.backendproject.util.OffsetBasedPageRequest;
import com.crust.backendproject.util.UtilClass;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImplementation implements TaskService {

    private final TaskRepository taskRepository;
    private final AuditAware auditAware;


    @Override
    public Task createTask(TaskRequest taskRequest) {

        return taskRepository.save(Task.builder()
                .description(taskRequest.getDescription())
                .title(taskRequest.getTitle())
                .build());
    }

    @Override
    public Task updateTask(Long taskId, TaskRequest updateTaskRequest) {

        Long userId = auditAware.getCurrentAuditor()
                .orElseThrow(() -> new AppException(Errors.UNAUTHORIZED));

        if (UtilClass.isNull(updateTaskRequest.getTitle()) && UtilClass.isNull(updateTaskRequest.getDescription()))
            throw new AppException(Errors.NOTHING_TO_UPDATE);

        Task task = taskRepository.findByIdAndCreatedBy(taskId, userId)
                .orElseThrow(() -> new AppException(Errors.TASK_NOT_FOUND));

        if (UtilClass.isNull(updateTaskRequest.getTitle()))
            task.setTitle(updateTaskRequest.getTitle());
        if (UtilClass.isNull(updateTaskRequest.getDescription()))
            task.setDescription(updateTaskRequest.getDescription());

        return taskRepository.save(task);

    }

    @Override
    public Task getTask(Long taskId) {

        Long userId = auditAware.getCurrentAuditor()
                .orElseThrow(() -> new AppException(Errors.UNAUTHORIZED));

        return taskRepository.findByIdAndCreatedBy(taskId, userId).orElseThrow(() -> new AppException(Errors.TASK_NOT_FOUND));

    }

    @Override
    public List<Task> getAllTasks(long offset, int limit) {

        Long userId = auditAware.getCurrentAuditor()
                .orElseThrow(() -> new AppException(Errors.UNAUTHORIZED));

        Pageable page = new OffsetBasedPageRequest(offset, limit, Sort.Direction.ASC, "name");
        return taskRepository.findAllByCreatedBy(userId, page);

    }

    @Override
    public MessageResponse deleteTask(Long taskId) {

        Long userId = auditAware.getCurrentAuditor()
                .orElseThrow(() -> new AppException(Errors.UNAUTHORIZED));

        taskRepository.deleteByIdAndCreatedBy(taskId, userId);

        return MessageResponse.builder().message("Task deleted successfully").build();

    }

}
