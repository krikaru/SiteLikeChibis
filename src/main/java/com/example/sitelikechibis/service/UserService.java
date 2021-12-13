package com.example.sitelikechibis.service;

import com.example.sitelikechibis.entity.Role;
import com.example.sitelikechibis.entity.User;
import com.example.sitelikechibis.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User create(User user) {
        user.setActive(true);
        user.getRoles().add(Role.ADMIN);
        return userRepo.save(user);
    }

    public User update(User userFromDb, User user) {
        userFromDb.setPassword(user.getPassword());
        return userRepo.save(userFromDb);
    }

    public void delete(User user) {
        userRepo.delete(user);
    }
}
