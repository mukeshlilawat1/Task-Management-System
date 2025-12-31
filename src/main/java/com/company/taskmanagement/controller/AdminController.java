package com.company.taskmanagement.controller;

import com.company.taskmanagement.dto.ApiResponse;
import com.company.taskmanagement.dto.RegisterRequest;
import com.company.taskmanagement.dto.UserResponse;
import com.company.taskmanagement.entity.User;
import com.company.taskmanagement.enums.Role;
import com.company.taskmanagement.exception.CustomException;
import com.company.taskmanagement.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //    Create User
    @PostMapping("/users")
    public ApiResponse<UserResponse> createUser(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .enabled(true)
                .build();

        return ApiResponse.success(
                "User Created",
                map(userRepository.save(user))
        );
    }

    //    get all Users
    @GetMapping("/users")
    public ApiResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());

        return ApiResponse.success("Users Fetched", users);
    }

    //    update role
    @PutMapping("/users/{id}/role")
    public ApiResponse<String> updateRole(@PathVariable Long id, @RequestParam Role role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found"));

        user.setRole(role);
        userRepository.save(user);

        return ApiResponse.success("Role updated");
    }

    //    Enable and disable users
    @PutMapping("/users/{id}/status")
    public ApiResponse<String> updateStatus(@PathVariable Long id, @RequestParam Boolean enabled) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("user not found"));

        user.setEnabled(enabled);
        userRepository.save(user);

        return ApiResponse.success("User status updated successfully");
    }

    private UserResponse map(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .build();
    }
}