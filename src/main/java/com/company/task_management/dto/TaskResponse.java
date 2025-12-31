package com.company.task_management.dto;

import com.company.task_management.enums.TaskPriority;
import com.company.task_management.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private String assignedTo;
    private String createdBy;
    private LocalDate dueDate;

}
