package ru.alex.task_managemen_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import ru.alex.task_managemen_system.model.dto.task.RegistrationTaskDTO;
import ru.alex.task_managemen_system.model.dto.task.TaskDTO;
import ru.alex.task_managemen_system.model.dto.user.LoginDTO;
import ru.alex.task_managemen_system.model.dto.user.UserDTO;
import ru.alex.task_managemen_system.model.response.JwtResponse;
import ru.alex.task_managemen_system.model.user.User;
import ru.alex.task_managemen_system.security.auth.DefaultAuthenticationProvider;
import ru.alex.task_managemen_system.service.AuthService;
import ru.alex.task_managemen_system.service.JwtService;
import ru.alex.task_managemen_system.service.UserService;
import ru.alex.task_managemen_system.util.exception.RegistrationUserException;
import ru.alex.task_managemen_system.util.validator.EmailValidator;
import ru.alex.task_managemen_system.util.validator.PasswordValidator;
import ru.alex.task_managemen_system.util.validator.UserRegistrationValidator;


import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service

@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final DefaultAuthenticationProvider authProvider;

    @Qualifier("defaultUserService")
    private final UserService userService;

    @Qualifier("defaultJwtService")
    private final JwtService jwtService;

    private final UserRegistrationValidator userRegistrationValidator;

    @Qualifier("defaultMailService")
    private final DefaultMailService mailService;

    public JwtResponse login(final LoginDTO loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authProvider.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        User user = userService.getUserByEmail(loginRequest.getEmail());

        jwtResponse.setUuid(user.getUuid());
        jwtResponse.setUsername(user.getEmail());
        jwtResponse.setAccessToken(jwtService.createAccessToken(user.getUuid(), user.getEmail(), user.getRoles()));
        jwtResponse.setRefreshToken(jwtService.createRefreshToken(user.getUuid(), user.getEmail()));
        ;
        return jwtResponse;
    }

    public User registration(final UserDTO userDTO, BindingResult bindingResult) throws ExecutionException, InterruptedException {

        userRegistrationValidator.validate(userDTO, bindingResult);
        List<Validator> validators = List.of(new EmailValidator(), new PasswordValidator());
        for (var i : validators) {
            if (i.supports(userDTO.getClass()))
                i.validate(userDTO, bindingResult);
        }
        if (bindingResult.hasErrors()) {
            throw new RegistrationUserException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        User user = userService.save(userDTO).get();

        mailService.send(userDTO.getEmail(), String.format("Hello, %s", userDTO.getEmail()),
                "Hello in my task app.\n This test email I'm sending email to check How it`s working");

        return user;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtService.refreshUserTokens(refreshToken);
    }
}
