package com.company.taskmanagement.service;

import com.company.taskmanagement.dto.LoginRequest;
import com.company.taskmanagement.dto.RegisterRequest;

public interface AuthService {
    String login(LoginRequest request);
    void register(RegisterRequest request);
}
