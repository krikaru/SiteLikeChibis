package com.example.sitelikechibis.config;

import com.example.sitelikechibis.config.util.RestAuthenticationFailureHandler;
import com.example.sitelikechibis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    @Autowired
    public WebSecurityConfig(@Lazy PasswordEncoder passwordEncoder,@Lazy UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/", "/login/**", "/js/**", "/error**", "/user/registration", "/img/**", "/activate/*", "/registration", "/news/**", "/api/news/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .failureHandler(authenticationFailureHandler())
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/").permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new RestAuthenticationFailureHandler();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

}
