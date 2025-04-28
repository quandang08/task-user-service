package com.amu.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponse {
    private String fullName;
    private String role;
    private String avatarUrl;
    private String phoneNumber;
    private String address;
    private String dateOfBirth;
    private String jobTitle;
    private String companyName;
    private String githubUrl;
    private String createdAt;
    private String updatedAt;
}
