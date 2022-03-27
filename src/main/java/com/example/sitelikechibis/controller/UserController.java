package com.example.sitelikechibis.controller;

import com.example.sitelikechibis.entity.User;
import com.example.sitelikechibis.entity.Views;
import com.example.sitelikechibis.entity.dto.UpdatedAttributeEntityDto;
import com.example.sitelikechibis.entity.dto.ValidationErrorResponse;
import com.example.sitelikechibis.service.UserService;
import com.example.sitelikechibis.vaidation.EntityUpdateValidator;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

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

    @DeleteMapping("{id}")
    public Boolean deleteUser(@PathVariable("id") User deletedUser,
                                           @AuthenticationPrincipal User principal) {
        if (deletedUser.getId().equals(principal.getId())){
            userService.delete(deletedUser);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @PutMapping(path = "{id}/userpic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @JsonView(Views.BaseUserInfo.class)
    public UpdatedAttributeEntityDto<User> uploadUserpic(
            @RequestParam("userpic") MultipartFile userpic,
            @PathVariable Long id,
            @AuthenticationPrincipal User principal
    ) throws IOException {
        User userFromDb = userService.findById(principal.getId()).get();

        User updatedUser = new User();
        updatedUser.setUserpic(userpic.getOriginalFilename());
        UpdatedAttributeEntityDto<User> updatedUserDto = new UpdatedAttributeEntityDto<>(updatedUser, "userpic");

//        List<String> errors;
//        if (id.equals(userFromDb.getId())) {
//            errors = validator.validateUser(updatedUserDto);
//
//            if (errors != null) {
//                updatedUserDto.setErrors(errors);
//            } else {
//                boolean result = saveUserpic(userpic, principal);
//                if (result) {
//                    userService.update(userFromDb, updatedUserDto);
//                }
//            }
//        } else {
//            errors = new ArrayList<>();
//            errors.add("Попытка поменять чужие данные!");
//            updatedUserDto.setErrors(errors);
//        }
        return updatedUserDto;
    }


    @PutMapping("{id}")
    @JsonView(Views.BaseUserInfo.class)
    public UpdatedAttributeEntityDto<User> updateProfile(
            @RequestBody UpdatedAttributeEntityDto<User> updatedUserDto,
            @PathVariable Long id,
            @AuthenticationPrincipal User principal
    ) {
//        User userFromDb = userService.findById(principal.getId()).get();
//        List<String> errors;
//        if (id.equals(userFromDb.getId())) {
//            errors = validator.validateUser(updatedUserDto);
//            if (errors != null) {
//                updatedUserDto.setErrors(errors);
//            } else {
//                userService.update(userFromDb, updatedUserDto);
//            }
//        } else {
//            errors = new ArrayList<>();
//            errors.add("Попытка поменять чужие данные!");
//            updatedUserDto.setErrors(errors);
//        }
        return updatedUserDto;
    }

    private boolean saveUserpic(MultipartFile avatar, User principal) throws IOException {
        if (avatar != null && !Objects.requireNonNull(avatar.getOriginalFilename()).isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + avatar.getOriginalFilename();

            avatar.transferTo(new File(uploadPath + "/" + resultFileName));

            principal.setUserpic(resultFileName);

            return true;
        }
        return false;
    }
}
