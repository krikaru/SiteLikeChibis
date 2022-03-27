package com.example.sitelikechibis.service;

import com.example.sitelikechibis.entity.Role;
import com.example.sitelikechibis.entity.User;
import com.example.sitelikechibis.entity.dto.ErrorInfo;
import com.example.sitelikechibis.entity.dto.UpdatedAttributeEntityDto;
import com.example.sitelikechibis.entity.dto.ValidationErrorResponse;
import com.example.sitelikechibis.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailSender;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, MailService mailSender) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
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

    public User update(User user, UpdatedAttributeEntityDto<User> updatedUser) {
        switch (updatedUser.getNameAttribute()) {
            case "name":
                user.setName(updatedUser.getUpdatedEntity().getName());
                break;
            case "password":
                user.setPassword(updatedUser.getUpdatedEntity().getPassword());
                break;
            case "email":
                user.setEmail(updatedUser.getUpdatedEntity().getEmail());
                break;
            case "userpic":
                user.setUserpic(updatedUser.getUpdatedEntity().getUserpic());
            default:
                break;
        }

        userRepo.save(user);

        return user;
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
        Optional<User> activatedUserOpt = userRepo.findByActivationCode(activationCode);
        if (activatedUserOpt.isPresent()) {
            User activatedUser = activatedUserOpt.get();
            activatedUser.setActivationCode(null);
            activatedUser.setActive(true);
            return true;
        } else {
            return false;
        }
    }
}
