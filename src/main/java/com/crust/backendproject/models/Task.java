package com.crust.backendproject.models;

import com.crust.backendproject.audit.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
@Entity
public class Task extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TASK_SEQ")
    @SequenceGenerator(name = "TASK_SEQ", sequenceName = "TASK_SEQ", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters long")
    @Column(name = "Title", length = 60)
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 3, max = 250, message = "Description must be between 3 and 250 characters long")
    @Column(name = "description", length = 260)
    private String description;

}
