package com.example.sitelikechibis.repo;

import com.example.sitelikechibis.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    @EntityGraph(attributePaths = {"roles", "news"})
    User findByUsername(String s);

    User findByActivationCode(String activationCode);

    boolean existsByEmail(String email);
}
