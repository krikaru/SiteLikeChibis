package com.example.sitelikechibis.entity.dto;

import com.example.sitelikechibis.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationFormDto {
    @Valid
    private User user;
    private String confirmPassword;
    private Map<String, String> errors;

    public RegistrationFormDto(@Valid User user, Map<String, String> errors) {
        this.user = user;
        this.errors = errors;
    }
}
