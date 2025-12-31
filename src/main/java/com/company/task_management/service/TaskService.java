package com.company.task_management.service;

import com.company.task_management.entity.Task;
import com.company.task_management.entity.User;

import java.util.List;

public interface TaskService {
    Task createTask(Task task, String creatorEmail);
    Task updateTask(Long taskId, Task task);

    List<Task> getMyTasks(String email);
    List<Task> getAllTasks();

    Task updateTaskStatus(Long taskId, String status);
}
