package com.amu.service;

import com.amu.entities.UserDetails;
import com.amu.repositories.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails getUserDetailsByUserId(Long userId) {
        return userDetailsRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User details not found"));
    }

    @Override
    public UserDetails updateUserDetails(Long userId, UserDetails newDetails) {
        UserDetails userDetails = getUserDetailsByUserId(userId);

        userDetails.setAvatarUrl(newDetails.getAvatarUrl());
        userDetails.setPhoneNumber(newDetails.getPhoneNumber());
        userDetails.setAddress(newDetails.getAddress());
        userDetails.setDateOfBirth(newDetails.getDateOfBirth());
        userDetails.setGithubUrl(newDetails.getGithubUrl());
        userDetails.setJobTitle(newDetails.getJobTitle());
        userDetails.setCompanyName(newDetails.getCompanyName());

        return userDetailsRepository.save(userDetails);
    }

    @Override
    public UserDetails createUserDetails(UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }
}
