package com.example.sitelikechibis.controller;

import com.example.sitelikechibis.controller.util.ControllerUtils;
import com.example.sitelikechibis.entity.User;
import com.example.sitelikechibis.entity.dto.RegistrationFormDto;
import com.example.sitelikechibis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @Autowired
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
    public RegistrationFormDto createUser(
            @Valid @RequestBody RegistrationFormDto registrationForm,
            BindingResult bindingResult
            ) {

        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            Map<String, String> validateErrors = ControllerUtils.getErrors(bindingResult);
            errors.putAll(validateErrors);
            return new RegistrationFormDto(null, errors);
        }

        return userService.create(registrationForm, errors);
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
