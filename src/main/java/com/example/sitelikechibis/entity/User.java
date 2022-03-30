package com.example.sitelikechibis.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@ToString(of = {"name", "email"})
@Entity
@Table(name = "usr")
@EqualsAndHashCode(of = {"id", "name"})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.BaseUserInfo.class)
    private Long id;

    @NotBlank(message = "Логин не должен быть пустым.")
    @Length(min=3, max=12)
    @Pattern(regexp = "^[a-zA-Z1-9]+$")
    private String username;

    @NotBlank(message = "Имя не должно быть пустым.", groups = {MarkerInterfaces.NameUpdate.class})
    @Length(message = "Длина имени должна быть не меньше 3 и не больше 25 символов.", max=25, min = 3, groups = {MarkerInterfaces.NameUpdate.class})
    @Pattern(message = "Имя может содержать только буквы русского и английского алфавита.",
            regexp = "^[a-zA-Zа-яА-ЯёЁ -]+$",
            flags = Pattern.Flag.CASE_INSENSITIVE, groups = {MarkerInterfaces.NameUpdate.class})
    @JsonView(Views.BaseUserInfo.class)
    private String name;

    @NotBlank(message = "Пароль должен быть заполнен.", groups = {MarkerInterfaces.PasswordUpdate.class})
    @Length(min=6, max=15, message = "Длина пароля должна быть не меньше 6 и не больше 15 символов.", groups = {MarkerInterfaces.PasswordUpdate.class})
    @Pattern(regexp = "^\\S+$", groups = {MarkerInterfaces.PasswordUpdate.class})
    private String password;

    @Transient
    @NotBlank(message = "Повторный пароль должен быть заполнен.")
    private String confirmPassword;

    @NotBlank(message = "Email должен быть заполнен.", groups = {MarkerInterfaces.EmailUpdate.class})
    @Email(message = "Неверный формат email.", groups = {MarkerInterfaces.EmailUpdate.class})
    @JsonView(Views.FullUserInfo.class)
    private String email;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    private boolean active;
    private String activationCode;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    @JsonView(Views.FullUserInfo.class)
    private List<News> news;

    @NotBlank(message = "Файл не выбран!Выберите файл.")
    @JsonView(Views.FullUserInfo.class)
    private String userpic;

    private static final long serialVersionUID = 3075591059030307505L;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
