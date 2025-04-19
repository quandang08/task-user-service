package com.amu.controller;

import com.amu.dto.user.UserResponse;
import com.amu.entities.User;
import com.amu.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile(@RequestHeader("Authorization") String jwt) {
        UserResponse user = userService.getUserProfile(jwt);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(@RequestHeader("Authorization") String jwt) {
        List<UserResponse> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }
}