package com.example.sitelikechibis.controller;

import com.example.sitelikechibis.entity.User;
import com.example.sitelikechibis.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUser() {
        return userService.findAll();
    }

    @GetMapping("{id}")
    public User getOneUser(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.create(user);
    }

    @PostMapping("{id}")
    public User updateUser(
            @PathVariable("id") User userFromDb,
            @RequestBody User user)
    {
        return userService.update(userFromDb, user);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") User user) {
        userService.delete(user);
    }
}
