package ru.alex.task_managemen_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.alex.task_managemen_system.model.dto.LoginDTO;
import ru.alex.task_managemen_system.model.response.JwtResponse;
import ru.alex.task_managemen_system.model.user.User;
import ru.alex.task_managemen_system.service.AuthService;

@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final AuthenticationManager authProvider;
    private final DefaultUserService userService;
    private final DefaultJwtService jwtService;

    public JwtResponse login(final LoginDTO loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authProvider.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword())
        );
        User user = userService.getUserByEmail(loginRequest.getEmail());
        jwtResponse.setUuid(user.getUuid());
        jwtResponse.setUsername(user.getEmail());
        jwtResponse.setAccessToken(jwtService.createAccessToken(
                user.getUuid(), user.getEmail(), user.getRoles())
        );
        jwtResponse.setRefreshToken(jwtService.createRefreshToken(
                user.getUuid(), user.getEmail())
        );
        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtService.refreshUserTokens(refreshToken);
    }
}
