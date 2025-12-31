package com.company.taskmanagement.repository;

import com.company.taskmanagement.entity.Task;
import com.company.taskmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedTo(User user);

    List<Task> findByCreatedBy(User user);

}
