package ru.alex.task_managemen_system.service;

import ru.alex.task_managemen_system.model.dto.LoginDTO;
import ru.alex.task_managemen_system.model.response.JwtResponse;

public interface AuthService {

    public JwtResponse login(final LoginDTO loginRequest);

    JwtResponse refresh(String refreshToken);
}
