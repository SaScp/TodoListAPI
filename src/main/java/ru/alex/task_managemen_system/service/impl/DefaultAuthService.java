package ru.alex.task_managemen_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import org.springframework.validation.BindingResult;
import ru.alex.task_managemen_system.model.dto.task.RegistrationTaskDTO;
import ru.alex.task_managemen_system.model.dto.task.TaskDTO;
import ru.alex.task_managemen_system.model.dto.user.LoginDTO;
import ru.alex.task_managemen_system.model.dto.user.UserDTO;
import ru.alex.task_managemen_system.model.response.JwtResponse;
import ru.alex.task_managemen_system.model.user.User;
import ru.alex.task_managemen_system.service.AuthService;
import ru.alex.task_managemen_system.util.exception.RegistrationUserException;
import ru.alex.task_managemen_system.util.validator.UserRegistrationValidator;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final AuthenticationManager authProvider;
    private final DefaultUserService userService;
    private final DefaultJwtService jwtService;
    private final UserRegistrationValidator userRegistrationValidator;

    public JwtResponse login(final LoginDTO loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();

        authProvider.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        User user = userService.getUserByEmail(loginRequest.getEmail());

        jwtResponse.setUuid(user.getUuid());
        jwtResponse.setUsername(user.getEmail());
        jwtResponse.setAccessToken(jwtService.createAccessToken(user.getUuid(), user.getEmail(), user.getRoles()));
        jwtResponse.setRefreshToken(jwtService.createRefreshToken(user.getUuid(), user.getEmail()));

        return jwtResponse;
    }

    public User registration(final UserDTO userDTO, BindingResult bindingResult) {

        userRegistrationValidator.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new RegistrationUserException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        User user = userService.save(userDTO);

        return user;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtService.refreshUserTokens(refreshToken);
    }
}
