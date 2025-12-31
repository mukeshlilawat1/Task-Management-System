package com.company.task_management.service;

import com.company.task_management.dto.LoginRequest;
import com.company.task_management.dto.RegisterRequest;

public interface AuthService {
    String login(LoginRequest request);
}
