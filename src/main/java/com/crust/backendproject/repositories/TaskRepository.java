package com.crust.backendproject.repositories;

import com.crust.backendproject.models.Task;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAllByCreatedBy(@NotNull @Min(1L) Long userId, Pageable pageable);

    Task findByIdAndCreatedBy(@NotNull @Min(1L) Long id, @NotNull @Min(1L) Long userId);

}
