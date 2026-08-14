package com.opentext.guesstheword.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opentext.guesstheword.service.AdminService;
import com.opentext.guesstheword.service.UserReport;

@RestController
@RequestMapping("/api/users")
public class UserReportController {

    private final AdminService adminService;

    public UserReportController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/report/{username}")
    public ResponseEntity<?> getUserReport(
            @PathVariable String username) {

        try {
            UserReport report =
                    adminService.getUserReport(username);

            return ResponseEntity.ok(report);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }
}