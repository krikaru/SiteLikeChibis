package com.example.sitelikechibis.controller;

import com.example.sitelikechibis.entity.UpdatableUserFields;
import com.example.sitelikechibis.entity.User;
import com.example.sitelikechibis.entity.dto.ErrorInfo;
import com.example.sitelikechibis.entity.dto.UpdatedUserpicDto;
import com.example.sitelikechibis.entity.dto.ValidationErrorResponse;
import com.example.sitelikechibis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @Value("${upload.path}")
    private String uploadPath;

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

    @PostMapping("registration")
    public ResponseEntity<ValidationErrorResponse> registration(
            @RequestBody User user)
    {
        return userService.registration(user);
    }

    @PutMapping(path = "{id}/userpic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UpdatedUserpicDto> uploadUserpic(
            @RequestParam("userpic") MultipartFile userpic,
            @PathVariable Long id,
            @AuthenticationPrincipal User principal
    ) throws IOException {
        UpdatedUserpicDto userpicDto = new UpdatedUserpicDto();

        if (id.equals(principal.getId())) {
            return userService.updateUserpic(id, userpic);
        } else {
            userpicDto.getErrors().add(
                    new ErrorInfo("id", "Попытка изменить чужие данные запрещена"));
            return new ResponseEntity<>(userpicDto, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("{id}/{nameField}")
    public ResponseEntity<ValidationErrorResponse> updateProfile(
            @RequestBody User updatedUser,
            @PathVariable Long id,
            @PathVariable String nameField,
            @AuthenticationPrincipal User principal)
    {
        ValidationErrorResponse response = new ValidationErrorResponse();
        try {
            UpdatableUserFields.valueOf(nameField.toUpperCase());
        } catch (IllegalArgumentException e) {
            response.getErrors().add(
                    new ErrorInfo("errorField", "Попытка изменить неизменяемые/несуществующие данные"));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (id.equals(principal.getId())) {
            updatedUser.setId(principal.getId());
            return userService.update(updatedUser, nameField);
        } else {
            response.getErrors().add(
                    new ErrorInfo("id", "Попытка изменить чужие данные запрещена"));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
