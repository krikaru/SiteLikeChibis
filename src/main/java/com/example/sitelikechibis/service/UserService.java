package com.example.sitelikechibis.service;

import com.example.sitelikechibis.entity.MarkerInterfaces;
import com.example.sitelikechibis.entity.Role;
import com.example.sitelikechibis.entity.UpdatableUserFields;
import com.example.sitelikechibis.entity.User;
import com.example.sitelikechibis.entity.dto.ErrorInfo;
import com.example.sitelikechibis.entity.dto.UpdatedUserpicDto;
import com.example.sitelikechibis.entity.dto.ValidationErrorResponse;
import com.example.sitelikechibis.repo.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailSender;
    private final Validator validator;

    @Value("${upload.path}")
    private String uploadPath;

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

    public ResponseEntity<ValidationErrorResponse> update(User updatedUser, String nameField) {
        User userFromDb = userRepo.findById(updatedUser.getId()).get();

        Set<ConstraintViolation<User>> validateError = validateAttribute(updatedUser, nameField);

        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        ResponseEntity<ValidationErrorResponse> responseEntity;

        if (validateError.size() == 0) {
            saveUpdateAttribute(userFromDb, updatedUser, nameField);
            responseEntity = new ResponseEntity<>(errorResponse, HttpStatus.OK);
        } else {
            ArrayList<ErrorInfo> errors = new ArrayList<>();
            for (ConstraintViolation<User> violation: validateError) {
                errors.add(new ErrorInfo(violation.getPropertyPath().toString(), violation.getMessage()));
            }
            errorResponse.setErrors(errors);
            responseEntity = new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    private Set<ConstraintViolation<User>> validateAttribute(User updatedUser, String nameField) {
        Set<ConstraintViolation<User>> validate = null;
        switch (UpdatableUserFields.valueOf(nameField.toUpperCase())) {
            case NAME:
                validate = validator.validate(updatedUser, MarkerInterfaces.NameUpdate.class);
                break;
            case PASSWORD:
                validate = validator.validate(updatedUser, MarkerInterfaces.PasswordUpdate.class);
                break;
            case EMAIL:
                validate = validator.validate(updatedUser, MarkerInterfaces.EmailUpdate.class);
                break;
        }
        return validate;
    }

    private void saveUpdateAttribute(User userFromDb, User updatedUser, String nameField) {
        switch (UpdatableUserFields.valueOf(nameField.toUpperCase())) {
            case NAME:
                userFromDb.setName(updatedUser.getName());
                break;
            case PASSWORD:
                userFromDb.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                break;
            case EMAIL:
                userFromDb.setEmail(updatedUser.getEmail());
                break;
        }
        userRepo.save(userFromDb);
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

    public ResponseEntity<UpdatedUserpicDto> updateUserpic(Long id, MultipartFile userpic) throws IOException {
        User userFromDb = userRepo.findById(id).get();

        UpdatedUserpicDto userpicDto = new UpdatedUserpicDto();
        ResponseEntity<UpdatedUserpicDto> responseEntity;

        if (userpic.getSize() < 5242880) {
            saveUserpic(userpic, userFromDb);
            userpicDto.setUserpicName(userFromDb.getUserpic());
            responseEntity = new ResponseEntity<>(userpicDto, HttpStatus.OK);

        } else if ("image/jpeg".equals(userpic.getContentType()) || "image/png".equals(userpic.getContentType())) {
            ArrayList<ErrorInfo> errors = new ArrayList<>();
            errors.add(new ErrorInfo("userpic", "Неверный формат файла!"));
            userpicDto.setErrors(errors);
            responseEntity = new ResponseEntity<>(userpicDto, HttpStatus.BAD_REQUEST);
        } else {
            ArrayList<ErrorInfo> errors = new ArrayList<>();
            errors.add(new ErrorInfo("userpic", "Размер аватара не должен превышать 5Мб!"));
            userpicDto.setErrors(errors);
            responseEntity = new ResponseEntity<>(userpicDto, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    private boolean saveUserpic(MultipartFile userpic, User updatedUser) throws IOException {
        if (userpic != null && !Objects.requireNonNull(userpic.getOriginalFilename()).isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + userpic.getOriginalFilename();

            userpic.transferTo(new File(uploadPath + "/" + resultFileName));

            updatedUser.setUserpic(resultFileName);
            userRepo.save(updatedUser);

            return true;
        }
        return false;
    }
}
