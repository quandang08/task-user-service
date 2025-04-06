package com.amu.service;

import com.amu.entities.User;

public interface UserService {

    User getUserProfile(String jwt);
}
