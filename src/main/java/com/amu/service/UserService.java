package com.amu.service;

import com.amu.dto.user.UserResponse;
import com.amu.entities.User;

import java.util.List;

public interface UserService {
    UserResponse getUserProfile(String jwt);
    List<UserResponse> getAllUser();
}