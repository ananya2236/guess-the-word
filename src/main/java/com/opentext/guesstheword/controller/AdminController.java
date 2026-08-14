package com.opentext.guesstheword.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opentext.guesstheword.model.User;
import com.opentext.guesstheword.repository.UserRepository;
import com.opentext.guesstheword.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserRepository userRepository;

    public AdminController(AdminService adminService,
                           UserRepository userRepository) {

        this.adminService = adminService;
        this.userRepository = userRepository;
    }

    @GetMapping("/report/daily")
    public ResponseEntity<?> getDailyReport(
            @RequestParam String username) {

        try {
            checkAdmin(username);

            return ResponseEntity.ok(
                    adminService.getDailyReport()
            );

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/report/user/{username}")
    public ResponseEntity<?> getUserReport(
            @PathVariable String username,
            @RequestParam String adminUsername) {

        try {
            checkAdmin(adminUsername);

            return ResponseEntity.ok(
                    adminService.getUserReport(username)
            );

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    private void checkAdmin(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Username not found"));

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException(
                    "Access denied. Admin only."
            );
        }
    }
}