package com.amu.controller;

import com.amu.dto.user.UserDetailsResponse;
import com.amu.entities.UserDetails;
import com.amu.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.amu.mapper.UserDetailsMapper;

@RestController
@RequestMapping("/api/user-details")
@RequiredArgsConstructor
public class UserDetailsController {
    private final UserDetailsService userDetailsService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailsResponse> getUserDetails(@PathVariable Long userId) {
        try {
            UserDetails userDetails = userDetailsService.getUserDetailsByUserId(userId);

            // Chuyển đổi từ UserDetails sang UserDetailsResponse thông qua UserDetailsMapper
            UserDetailsResponse response = UserDetailsMapper.toUserDetailsResponse(userDetails);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDetails> updateUserDetails(@PathVariable Long userId, @RequestBody UserDetails userDetails) {
        try {
            userDetailsService.updateUserDetails(userId, userDetails);
            return ResponseEntity.ok(userDetails);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
