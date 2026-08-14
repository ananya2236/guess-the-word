package com.opentext.guesstheword.service;

import org.springframework.stereotype.Service;

import com.opentext.guesstheword.model.User;
import com.opentext.guesstheword.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username, String password, String role) {

    if (username == null || username.length() < 5) {
        throw new RuntimeException("Username must have at least 5 letters");
    }

    if (!username.matches("[a-zA-Z]+")) {
        throw new RuntimeException("Username must contain only letters");
    }

    if (password == null || password.length() < 5) {
        throw new RuntimeException("Password must have at least 5 characters");
    }

    if (!password.matches(".*[a-zA-Z].*")) {
        throw new RuntimeException("Password must contain a letter");
    }

    if (!password.matches(".*[0-9].*")) {
        throw new RuntimeException("Password must contain a number");
    }

    if (!password.matches(".*[$%*].*")) {
        throw new RuntimeException("Password must contain $, % or *");
    }

    if (userRepository.existsByUsername(username)) {
        throw new RuntimeException("Username already exists");
    }

    User user = new User(username, password, role);

    return userRepository.save(user);
}

    public User loginUser(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
}