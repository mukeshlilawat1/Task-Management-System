package com.company.taskmanagement.service;

import com.company.taskmanagement.entity.Task;

import java.util.List;

public interface TaskService {
    Task createTask(Task task, String creatorEmail);
    Task updateTask(Long taskId, Task task);

    List<Task> getMyTasks(String email);
    List<Task> getAllTasks();

    Task updateTaskStatus(Long taskId, String status);
}
