package com.amu.repositories;

import com.amu.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findByFullName(String fullName);

    public User findByEmail(String email);
}
