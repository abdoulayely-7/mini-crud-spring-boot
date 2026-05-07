package com.example.demo.application.service;

import com.example.demo.application.mapper.UserMapper;
import com.example.demo.domain.exception.EmailAlreadyExistsException;
import com.example.demo.domain.model.User;
import com.example.demo.infrastructure.repository.UserRepository;
import com.example.demo.presentation.dto.request.CreateUserRequest;
import com.example.demo.presentation.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = UserMapper.toEntity(request);

        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }
}
