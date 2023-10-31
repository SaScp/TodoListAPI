package ru.alex.task_managemen_system.service;

import org.springframework.security.core.Authentication;
import ru.alex.task_managemen_system.model.user.Role;

import java.util.Set;
import java.util.UUID;

public interface JwtService {
    public String createAccessToken(String uuid, String email, Role role);

    public String createRefreshToken(String uuid, String email);

    public boolean validatorAccessToken(String token);

    public String getUsername(String token);

    public boolean validatorRefreshToken(String token);

    public String getUUID(String token);

    public Authentication getAuthentication(String token);
}
