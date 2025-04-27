package com.amu.service;


import com.amu.entities.UserDetails;

public interface UserDetailsService {
    UserDetails getUserDetailsByUserId(Long userId);
    UserDetails updateUserDetails(Long userId, UserDetails userDetails);
    UserDetails createUserDetails(UserDetails userDetails);
}