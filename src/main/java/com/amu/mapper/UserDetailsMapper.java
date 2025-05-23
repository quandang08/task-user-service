package com.amu.mapper;

import com.amu.dto.user_detail.UserDetailsResponse;
import com.amu.entities.UserDetails;

public class UserDetailsMapper {

    // Chuyển đổi từ UserDetails sang UserDetailsResponse
    public static UserDetailsResponse toUserDetailsResponse(UserDetails userDetails) {
        return new UserDetailsResponse(
                userDetails.getUser().getFullName(),
                userDetails.getUser().getRole(),
                userDetails.getUser().getEmail(),
                userDetails.getAvatarUrl(),
                userDetails.getPhoneNumber(),
                userDetails.getAddress(),
                userDetails.getDateOfBirth() != null ? userDetails.getDateOfBirth().toString() : null,
                userDetails.getJobTitle(),
                userDetails.getCompanyName(),
                userDetails.getGithubUrl(),
                userDetails.getCreatedAt() != null ? userDetails.getCreatedAt().toString() : null,
                userDetails.getUpdatedAt() != null ? userDetails.getUpdatedAt().toString() : null
        );
    }

}
