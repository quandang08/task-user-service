package com.amu.service;

import com.amu.dto.user_detail.UpdateUserDetailsRequest;
import com.amu.entities.User;
import com.amu.entities.UserDetails;
import com.amu.repositories.UserDetailsRepository;
import com.amu.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    private final UserRepository userRepository;

    @Override
    public UserDetails getUserDetailsByUserId(Long userId) {
        return userDetailsRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User details not found"));
    }

    @Transactional
    @Override
    public UserDetails updateUserDetails(Long userId, UpdateUserDetailsRequest newDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDetails userDetails = userDetailsRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("UserDetails not found"));

        // Cập nhật User
        if (newDetails.getFullName() != null) {
            user.setFullName(newDetails.getFullName());
        }
        userRepository.save(user);

        // Cập nhật UserDetails
        if (newDetails.getAvatarUrl() != null) {
            userDetails.setAvatarUrl(newDetails.getAvatarUrl());
        }
        if (newDetails.getPhoneNumber() != null) {
            userDetails.setPhoneNumber(newDetails.getPhoneNumber());
        }
        if (newDetails.getAddress() != null) {
            userDetails.setAddress(newDetails.getAddress());
        }
        if (newDetails.getGithubUrl() != null) {
            userDetails.setGithubUrl(newDetails.getGithubUrl());
        }
        if (newDetails.getJobTitle() != null) {
            userDetails.setJobTitle(newDetails.getJobTitle());
        }
        if (newDetails.getCompanyName() != null) {
            userDetails.setCompanyName(newDetails.getCompanyName());
        }
        if (newDetails.getDateOfBirth() != null) {
            userDetails.setDateOfBirth(LocalDate.parse(newDetails.getDateOfBirth()).atStartOfDay());
        }

        return userDetailsRepository.save(userDetails);
    }

    @Override
    public UserDetails createUserDetails(UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }
}
