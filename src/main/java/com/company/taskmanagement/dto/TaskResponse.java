package com.company.taskmanagement.dto;

import com.company.taskmanagement.enums.TaskPriority;
import com.company.taskmanagement.enums.TaskStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
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
