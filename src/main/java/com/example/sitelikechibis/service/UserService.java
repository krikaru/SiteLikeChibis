package com.example.sitelikechibis.service;

import com.example.sitelikechibis.entity.Role;
import com.example.sitelikechibis.entity.User;
import com.example.sitelikechibis.entity.dto.RegistrationFormDto;
import com.example.sitelikechibis.entity.dto.UpdatedAttributeUserDto;
import com.example.sitelikechibis.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

    public RegistrationFormDto registration(RegistrationFormDto registrationForm, Map<String, String> errors) {
        User user = registrationForm.getUser();

        boolean isExistsEmail = userRepo.existsByEmail(user.getEmail());
        if (isExistsEmail) {
            errors.put("uniqueEmailError", "Аккаунт с таким email уже существует");
            return new RegistrationFormDto(user, errors);
        }

        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb == null) {
            String confirmPassword = registrationForm.getConfirmPassword();
            if (!confirmPassword.equals(user.getPassword())) {
                errors.put("confirmPasswordError", "Пароли не совпадают");
                return new RegistrationFormDto(user, errors);
            }

            user.setActive(false);
            user.setUserpic("default.jpg");
            user.getRoles().add(Role.ADMIN);
            user.setActivationCode(UUID.randomUUID().toString());
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            User savedUser = userRepo.save(user);

            sendMessage(savedUser);

            return new RegistrationFormDto(savedUser, errors);
        } else {
            errors.put("usernameError", "Такой логин уже существует. Придумайте другой.");
            return new RegistrationFormDto(user, errors);
        }
    }

    private void sendMessage(User user) {
        String message = String.format(
                "Здравствуйте, %s! \n" +
                        "Добро пожаловать на сайт компании ООО ЧИБИС. Для завершения регистрации и подтверждения вашей почты пройдите по этой ссылке: http://localhost:9000/activate/%s",
                user.getName(), user.getActivationCode()
        );
        mailSender.send(user.getEmail(), "Activation code", message);
    }

    public User update(User user, UpdatedAttributeUserDto updatedUser) {
        switch (updatedUser.getNameAttribute()) {
            case "name":
                user.setName(updatedUser.getUpdatedUser().getName());
                break;
            case "password":
                user.setPassword(updatedUser.getUpdatedUser().getPassword());
                break;
            case "email":
                user.setEmail(updatedUser.getUpdatedUser().getEmail());
                break;
            case "userpic":
                user.setUserpic(updatedUser.getUpdatedUser().getUserpic());
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
