package ru.alex.task_managemen_system.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.alex.task_managemen_system.model.user.Role;
import ru.alex.task_managemen_system.service.JwtService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("defaultJwtServiceBean")
public class DefaultJwtService implements JwtService {


    public String createAccessToken(String uuid,
                                    String email,
                                    Set<Role> roles) {

        return null;
    }

    public String createRefreshToken(String uuid,
                                    String email) {

        return null;
    }


    public boolean validator(String token) {
        return false;
    }

    public String getUUID(String token) {
        return null;
    }

    private String getUsername(String token) {
        return null;
    }

    public Authentication getAuthentication(String token) {
        return null;
    }

    private List<String> roleToList(Set<Role> roles) {
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
