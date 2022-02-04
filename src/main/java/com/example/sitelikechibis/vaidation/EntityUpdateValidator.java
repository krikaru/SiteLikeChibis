package com.example.sitelikechibis.vaidation;

import com.example.sitelikechibis.entity.MarkerInterfaces;
import com.example.sitelikechibis.entity.dto.UpdatedAttributeUserDto;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EntityUpdateValidator {
    private final Validator validator;

    public EntityUpdateValidator(Validator validator) {
        this.validator = validator;
    }

    public List<String> validateUser(UpdatedAttributeUserDto updatedUser) {
        Class<? extends MarkerInterfaces.AttributeUpdate> attributeUpdate = getValidatedUserAttributeClass(updatedUser.getNameAttribute());
        List<String> errors;

        Set<ConstraintViolation<UpdatedAttributeUserDto>> validate = validator.validate(updatedUser, attributeUpdate);
        if (!validate.isEmpty()) {
            errors = validate.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            return errors;
        }
        return null;
    }

    private Class<? extends MarkerInterfaces.AttributeUpdate> getValidatedUserAttributeClass(String nameAttribute) {
        switch (nameAttribute) {
            case "name":
                return MarkerInterfaces.NameUpdate.class;
            case "password":
                return MarkerInterfaces.PasswordUpdate.class;
            case "email":
                return MarkerInterfaces.EmailUpdate.class;
            case "userpic":
                return MarkerInterfaces.UserpicUpdate.class;
            default:
                return MarkerInterfaces.AttributeUpdate.class;

        }
    }
}
