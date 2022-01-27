package com.example.sitelikechibis.controller;

import com.example.sitelikechibis.controller.util.ControllerUtils;
import com.example.sitelikechibis.entity.User;
import com.example.sitelikechibis.entity.Views;
import com.example.sitelikechibis.entity.dto.RegistrationFormDto;
import com.example.sitelikechibis.entity.dto.UpdatedAttributeUserDto;
import com.example.sitelikechibis.service.UserService;
import com.example.sitelikechibis.vaidation.EntityUpdateValidator;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final EntityUpdateValidator validator;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public UserController(UserService userService, EntityUpdateValidator validator) {
        this.userService = userService;
        this.validator = validator;
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
    public RegistrationFormDto registration(
            @Valid @RequestBody RegistrationFormDto registrationForm,
            BindingResult bindingResult
            ) {
        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            Map<String, String> validateErrors = ControllerUtils.getErrors(bindingResult);
            errors.putAll(validateErrors);
            return new RegistrationFormDto(registrationForm.getUser(), errors);
        }

        return userService.registration(registrationForm, errors);
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

//    @PostMapping(path = "userpic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public boolean uploadUserpic(
//            @RequestParam("userpic") MultipartFile userpic,
//            @AuthenticationPrincipal User principal
//    ) throws IOException {
//        boolean result = saveUserpic(userpic, principal);
//        if (result) {
//            userService.update(principal);
//            return true;
//        }
//        return false;
//    }


    @PatchMapping("{id}")
    @JsonView(Views.BaseUserInfo.class)
    public UpdatedAttributeUserDto updateProfile(
            @RequestBody UpdatedAttributeUserDto updatedUserDto,
            @PathVariable Long id,
            @AuthenticationPrincipal User principal
    ) {
        User userFromDb = userService.findById(principal.getId()).get();
        List<String> errors;
        if (id.equals(userFromDb.getId())) {
            errors = validator.validateUser(updatedUserDto);
            if (errors != null) {
                updatedUserDto.setErrors(errors);
            } else {
                userService.update(userFromDb, updatedUserDto);
            }
        } else {
            errors = new ArrayList<>();
            errors.add("Попытка поменять чужие данные!");
            updatedUserDto.setErrors(errors);
        }
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
