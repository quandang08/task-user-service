package com.amu.service;


import com.amu.dto.user_detail.UpdateUserDetailsRequest;
import com.amu.entities.UserDetails;

public interface UserDetailsService {
    UserDetails getUserDetailsByUserId(Long userId);
    UserDetails updateUserDetails(Long userId, UpdateUserDetailsRequest userDetails);
    UserDetails createUserDetails(UserDetails userDetails);
}