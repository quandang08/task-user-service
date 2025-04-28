package com.amu.dto.user_detail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDetailsRequest {
    private String avatarUrl;
    private String fullName;
    private String address;
    private String phoneNumber;
    private String githubUrl;
    private String jobTitle;
    private String companyName;
    private String dateOfBirth;

}
