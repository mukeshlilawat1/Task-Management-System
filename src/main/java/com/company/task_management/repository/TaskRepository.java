package com.company.task_management.repository;

import com.company.task_management.entity.Task;
import com.company.task_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedTo(User user);

    List<Task> findByCreatedBy(User user);

}
