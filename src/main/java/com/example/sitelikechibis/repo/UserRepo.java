package com.example.sitelikechibis.repo;

import com.example.sitelikechibis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
