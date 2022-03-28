package com.example.sitelikechibis.service;

import com.example.sitelikechibis.entity.MarkerInterfaces;
import com.example.sitelikechibis.entity.Role;
import com.example.sitelikechibis.entity.UpdatableUserFields;
import com.example.sitelikechibis.entity.User;
import com.example.sitelikechibis.entity.dto.ErrorInfo;
import com.example.sitelikechibis.entity.dto.ValidationErrorResponse;
import com.example.sitelikechibis.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailSender;
    private final Validator validator;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, MailService mailSender, Validator validator) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.validator = validator;
    }


    public List<User> findAll() {
        return userRepo.findAll();
    }

    public ResponseEntity<ValidationErrorResponse> registration(User newUser) {
        ValidationErrorResponse errors = new ValidationErrorResponse();

        boolean isExistsEmail = userRepo.existsByEmail(newUser.getEmail());
        if (isExistsEmail) {
            errors.getErrors().add(new ErrorInfo("email", "Аккаунт с таким email уже существует"));
        }

        String confirmPassword = newUser.getConfirmPassword();
        if (!confirmPassword.equals(newUser.getPassword())) {
            errors.getErrors().add(new ErrorInfo("password", "Пароли не совпадают"));
        }

        User userFromDb = userRepo.findByUsername(newUser.getUsername());
        if (userFromDb != null) {
            errors.getErrors().add(new ErrorInfo("username", "Такой логин уже существует. Придумайте другой."));
        } else if (errors.getErrors().size() == 0){
            newUser.setActive(false);
            newUser.setUserpic("default.jpg");
            newUser.getRoles().add(Role.ADMIN);
            newUser.setActivationCode(UUID.randomUUID().toString());
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

            User savedUser = userRepo.save(newUser);

            sendMessage(savedUser);

            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    private void sendMessage(User user) {
        String message = String.format(
                "Здравствуйте, %s! \n" +
                        "Добро пожаловать на сайт компании ООО ЧИБИС. Для завершения регистрации и подтверждения вашей почты пройдите по этой ссылке: http://localhost:9000/activate/%s",
                user.getName(), user.getActivationCode()
        );
        mailSender.send(user.getEmail(), "Activation code", message);
    }

    public ValidationErrorResponse update(User updatedUser, String nameField) {
        User userFromDb = userRepo.findById(updatedUser.getId()).get();

        Set<ConstraintViolation<User>> validateError = validateAttribute(updatedUser, nameField);

        ValidationErrorResponse userDto = new ValidationErrorResponse();
        if (validateError.size() == 0) {
            saveUpdateAttribute(userFromDb::setName, updatedUser::getName, userFromDb);
        } else {
            ArrayList<ErrorInfo> errors = new ArrayList<>();
            for (ConstraintViolation<User> violation: validateError) {
                errors.add(new ErrorInfo(violation.getPropertyPath().toString(), violation.getMessage()));
            }
            userDto.setErrors(errors);
        }

        return userDto;
    }

    private Set<ConstraintViolation<User>> validateAttribute(User updatedUser, String nameField) {
        Set<ConstraintViolation<User>> validate = null;
        switch (UpdatableUserFields.valueOf(nameField.toUpperCase())) {
            case NAME:
                validate = validator.validate(updatedUser, MarkerInterfaces.NameUpdate.class);
                break;
            case PASSWORD:
                validate = validator.validate(updatedUser, MarkerInterfaces.PasswordUpdate.class);
                if (validate.size() == 0) updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                break;
            case EMAIL:
                validate = validator.validate(updatedUser, MarkerInterfaces.EmailUpdate.class);
                break;
        }
        return validate;
    }

    private void saveUpdateAttribute(Consumer<String> setMethod, Supplier<String> getMethod, User userFromDb) {
        setMethod.accept(getMethod.get());
        userRepo.save(userFromDb);
    }

    public void delete(User user) {
        userRepo.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(s);
        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    public boolean findByActivationCode(String activationCode) {
        User activatedUser = userRepo.findByActivationCode(activationCode);
        if (activatedUser != null) {
            activatedUser.setActivationCode(null);
            activatedUser.setActive(true);
            userRepo.save(activatedUser);
            return true;
        } else {
            return false;
        }
    }
}
