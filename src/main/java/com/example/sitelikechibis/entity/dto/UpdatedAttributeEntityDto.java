package com.example.sitelikechibis.entity.dto;

import com.example.sitelikechibis.entity.User;
import com.example.sitelikechibis.entity.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
@AllArgsConstructor
public class UpdatedAttributeEntityDto<T> {
    @Valid
    @JsonView(Views.BaseUserInfo.class)
    private T updatedEntity;
    @JsonView(Views.BaseUserInfo.class)
    private String nameAttribute;
    @JsonView(Views.BaseUserInfo.class)
    private List<String> errors;

    public UpdatedAttributeEntityDto(@Valid T updatedEntity, String nameAttribute) {
        this.updatedEntity = updatedEntity;
        this.nameAttribute = nameAttribute;
    }
}
