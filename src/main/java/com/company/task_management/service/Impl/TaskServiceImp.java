package com.company.task_management.service.Impl;

import com.company.task_management.entity.Task;
import com.company.task_management.entity.User;
import com.company.task_management.enums.TaskStatus;
import com.company.task_management.exception.CustomException;
import com.company.task_management.repository.TaskRepository;
import com.company.task_management.repository.UserRepository;
import com.company.task_management.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImp implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Task createTask(Task task, String creatorEmail) {
        User creator = userRepository.findByEmail(creatorEmail)
                .orElseThrow(() -> new CustomException("Creator not found"));

        task.setCreatedBy(creator);
        task.setStatus(TaskStatus.PENDING);

        return taskRepository.save(task);

    }

    @Override
    public Task updateTask(Long taskId, Task task) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException("Task not found"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
         existingTask.setPriority(task.getPriority());
         existingTask.setDueDate(task.getDueDate());

         if (task.getAssignedTo() != null) {
             User assignedUser = userRepository.findById(
                     task.getAssignedTo().getId()
             ).orElseThrow(() -> new CustomException("Assinged user not found"));
             existingTask.setAssignedTo(assignedUser);
         }
         return taskRepository.save(existingTask);
    }

    @Override
    public List<Task> getMyTasks(String email) {
      User user = userRepository.findByEmail(email)
              .orElseThrow(() -> new CustomException("User not found"));

      return taskRepository.findByAssignedTo(user);
    }

    @Override
    public List<Task> getAllTasks() {
       return taskRepository.findAll();
    }

    @Override
    public Task updateTaskStatus(Long taskId, String status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException("Task not found"));

        TaskStatus newStatus;
        try {
            newStatus = TaskStatus.valueOf(status.toUpperCase());
        }catch (IllegalArgumentException e) {
            throw new CustomException("Invalid task status");
        }

        task.setStatus(newStatus);
        return taskRepository.save(task);
    }
}
