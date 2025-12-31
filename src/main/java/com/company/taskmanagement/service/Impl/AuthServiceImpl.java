package com.company.taskmanagement.service.impl;

import com.company.taskmanagement.dto.LoginRequest;
import com.company.taskmanagement.dto.RegisterRequest;
import com.company.taskmanagement.entity.User;
import com.company.taskmanagement.enums.Role;
import com.company.taskmanagement.repository.UserRepository;
import com.company.taskmanagement.security.JwtUtil;
import com.company.taskmanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // 🔥 THIS WAS MISSING
    @Autowired
    private UserRepository userRepository;

    @Override
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Bad credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }


    @Override
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // 🔥 MUST
                .role(Role.EMPLOYEE) // or ADMIN
                .enabled(true)
                .build();

        userRepository.save(user);
    }
}
