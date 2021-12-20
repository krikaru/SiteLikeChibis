package com.example.sitelikechibis.service;

import com.example.sitelikechibis.entity.Role;
import com.example.sitelikechibis.entity.User;
import com.example.sitelikechibis.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User create(User user) {
        user.setActive(true);
        user.getRoles().add(Role.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public User update(User userFromDb, User user) {
        userFromDb.setPassword(user.getPassword());
        return userRepo.save(userFromDb);
    }

    public void delete(User user) {
        userRepo.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(s);
        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
