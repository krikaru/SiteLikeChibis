package com.example.sitelikechibis.entity.dto;

import com.example.sitelikechibis.entity.User;
import com.example.sitelikechibis.entity.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class UpdatedAttributeUserDto {
    @Valid
    @JsonView(Views.BaseUserInfo.class)
    private User updatedUser;
    @JsonView(Views.BaseUserInfo.class)
    private String nameAttribute;
    @JsonView(Views.BaseUserInfo.class)
    private List<String> errors;

    public UpdatedAttributeUserDto(@Valid User updatedUser, String nameAttribute) {
        this.updatedUser = updatedUser;
        this.nameAttribute = nameAttribute;
    }
}
