package ru.alex.task_managemen_system.service;

import org.springframework.validation.BindingResult;
import ru.alex.task_managemen_system.model.dto.user.LoginDTO;
import ru.alex.task_managemen_system.model.dto.user.UserDTO;
import ru.alex.task_managemen_system.model.response.JwtResponse;
import ru.alex.task_managemen_system.model.user.User;

import java.util.concurrent.ExecutionException;

public interface AuthService {

    JwtResponse login(final LoginDTO loginRequest);

    JwtResponse refresh(String refreshToken);

    User registration(final UserDTO taskDTO, BindingResult bindingResult) throws ExecutionException, InterruptedException;
}
