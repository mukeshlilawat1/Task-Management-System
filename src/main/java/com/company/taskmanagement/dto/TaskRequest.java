package com.company.taskmanagement.dto;

import com.company.taskmanagement.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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
