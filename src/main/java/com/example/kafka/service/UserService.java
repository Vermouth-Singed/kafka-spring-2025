package com.example.kafka.service;

import com.example.kafka.dto.UserRequest;
import com.example.kafka.dto.UserResponse;
import com.example.kafka.model.User;
import com.example.kafka.producer.UserProducer;
import com.example.kafka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProducer userProducer;

    // CREATE
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다: " + request.getEmail());
        }

        User user = User.builder().
            name(request.getName()).
            email(request.getEmail()).
            phone(request.getPhone()).
            build();

        User savedUser = userRepository.save(user);

        // Kafka 이벤트 발행
        userProducer.sendUserEvent("CREATE", savedUser);

        return convertToResponse(savedUser);
    }

    // READ
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + id));
        return convertToResponse(user);
    }

    // UPDATE
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + id));

        user.update(request.getName(), request.getEmail(), request.getPhone());

        User updatedUser = userRepository.save(user);

        // Kafka 이벤트 발행
        userProducer.sendUserEvent("UPDATE", updatedUser);

        return convertToResponse(updatedUser);
    }

    // DELETE
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + id));

        userRepository.deleteById(id);

        // Kafka 이벤트 발행
        userProducer.sendUserEvent("DELETE", user);
    }

    private UserResponse convertToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
