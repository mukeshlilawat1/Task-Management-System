package com.company.taskmanagement.controller;

import com.company.taskmanagement.dto.ApiResponse;
import com.company.taskmanagement.dto.TaskRequest;
import com.company.taskmanagement.dto.TaskResponse;
import com.company.taskmanagement.entity.Task;
import com.company.taskmanagement.entity.User;
import com.company.taskmanagement.exception.CustomException;
import com.company.taskmanagement.repository.UserRepository;
import com.company.taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    // CREATE TASK
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ApiResponse<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request,
            Authentication auth) {

        User assignedTo = null;
        if (request.getAssignedToUserId() != null) {
            assignedTo = userRepository.findById(request.getAssignedToUserId())
                    .orElseThrow(() -> new CustomException("Assigned user not found"));
        }

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .assignedTo(assignedTo)
                .dueDate(request.getDueDate())
                .build();

        return ApiResponse.success(
                "Task created",
                map(taskService.createTask(task, auth.getName()))
        );
    }

    // MY TASKS
    @GetMapping("/my")
    public ApiResponse<List<TaskResponse>> myTasks(Authentication auth) {

        return ApiResponse.success(
                "My tasks",
                taskService.getMyTasks(auth.getName())
                        .stream()
                        .map(this::map)
                        .collect(Collectors.toList())
        );
    }

    // ALL TASKS (ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<TaskResponse>> allTasks() {

        return ApiResponse.success(
                "All tasks",
                taskService.getAllTasks()
                        .stream()
                        .map(this::map)
                        .collect(Collectors.toList())
        );
    }

    // UPDATE TASK STATUS
    @PutMapping("/{id}/status")
    public ApiResponse<TaskResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return ApiResponse.success(
                "Task status updated",
                map(taskService.updateTaskStatus(id, status))
        );
    }

    private TaskResponse map(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .assignedTo(
                        task.getAssignedTo() != null
                                ? task.getAssignedTo().getEmail()
                                : null
                )
                .createdBy(task.getCreatedBy().getEmail())
                .dueDate(task.getDueDate())
                .build();
    }
}
