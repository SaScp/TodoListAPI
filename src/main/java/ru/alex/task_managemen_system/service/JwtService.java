package ru.alex.task_managemen_system.service;

import org.springframework.security.core.Authentication;
import ru.alex.task_managemen_system.model.response.JwtResponse;
import ru.alex.task_managemen_system.model.user.Role;

public interface JwtService {
    String createAccessToken(String uuid, String email, Role role);

    String createRefreshToken(String uuid, String email);

    boolean validatorAccessToken(String token);

    String getUsername(String token);

    boolean validatorRefreshToken(String token);

    String getRefreshUUID(String token);

    JwtResponse refreshUserTokens(String refreshToken);

    String getAccessUUID(String token);

    Authentication getAuthentication(String token);
}
