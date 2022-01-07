package com.example.sitelikechibis.controller;

import com.example.sitelikechibis.entity.User;
import com.example.sitelikechibis.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("profile")
public class ProfileController {
    private final UserService userService;

    @Value("${upload.path}")
    private String uploadPath;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "userpic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public boolean uploadUserpic(
            @RequestParam("userpic") MultipartFile userpic,
            @AuthenticationPrincipal User principal
    ) throws IOException {
        boolean result = saveUserpic(userpic, principal);
        if (result) {
            userService.update(principal);
            return true;
        }
        return false;
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
