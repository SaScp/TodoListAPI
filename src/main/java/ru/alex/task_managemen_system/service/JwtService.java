package ru.alex.task_managemen_system.service;

import org.springframework.security.core.Authentication;
import ru.alex.task_managemen_system.model.user.Role;

import java.util.Set;

public interface JwtService {
    public String createAccessToken(String uuid, String email, Set<Role> roles);

    public String createRefreshToken(String uuid, String email);

    public boolean validator(String token);

    public String getUUID(String token);

    public Authentication getAuthentication(String token);
}
