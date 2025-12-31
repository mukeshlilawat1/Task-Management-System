package com.company.task_management.dto;

import com.company.task_management.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@Setter
public class TaskRequest {
    @NotBlank
    private String title;

    private String description;

    @NotNull
    private TaskPriority priority;

    private Long assignedToUserId;

    private LocalDate dueDate;

}
