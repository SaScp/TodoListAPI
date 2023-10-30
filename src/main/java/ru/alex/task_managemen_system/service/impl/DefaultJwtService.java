package ru.alex.task_managemen_system.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.alex.task_managemen_system.model.response.JwtResponse;
import ru.alex.task_managemen_system.model.user.Role;
import ru.alex.task_managemen_system.model.user.User;
import ru.alex.task_managemen_system.security.auth.UserDetailsImpl;
import ru.alex.task_managemen_system.security.auth.UserDetailsServiceImpl;
import ru.alex.task_managemen_system.service.JwtService;
import ru.alex.task_managemen_system.util.exception.AccessDeniedException;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultJwtService implements JwtService {

    private final DefaultUserService userService;

    private final UserDetailsServiceImpl userDetailsService;

    @Value("${jwt.secret.access}")
    private String accessSecret;

    @Value("${jwt.secret.refresh}")
    private String refreshSecret;

    private static final  ZonedDateTime now = ZonedDateTime.now();

    public String createAccessToken(String uuid,
                                    String email,
                                    Role role) {

        return JWT.create()
                .withSubject("user")
                .withClaim("id", uuid.toString())
                .withClaim("email", email).
                withClaim("roles", getRole(role))
                .withIssuedAt(Instant.from(now))
                .withIssuer("Server")
                .withExpiresAt(Instant.from(now.plusMinutes(90)))
                .sign(Algorithm.HMAC256(accessSecret));
    }


    public String createRefreshToken(String uuid,
                                    String email) {

        return JWT.create()
                .withSubject("user")
                .withClaim("id", uuid.toString())
                .withClaim("email", email)
                .withIssuedAt(Instant.from(now))
                .withIssuer("Server")
                .withExpiresAt(Instant.from(now.plusDays(60)))
                .sign(Algorithm.HMAC256(refreshSecret));
    }

    public JwtResponse refreshUserTokens(String refreshToken) {
        JwtResponse jwtResponse = new JwtResponse();

        if (!validatorRefreshToken(refreshToken)) {
            throw new AccessDeniedException();
        }

        String id = getUUID(refreshToken);
        User user = userService.getUserByUUID(id);

        jwtResponse.setUuid(id);
        jwtResponse.setUsername(user.getEmail());
        jwtResponse.setAccessToken(createAccessToken(id, user.getEmail(), user.getRoles()));
        jwtResponse.setRefreshToken(createRefreshToken(id, user.getEmail()));

        return jwtResponse;
    }

    public boolean validatorAccessToken(String token) {
        return !Date.from(now.toInstant()).equals(getVerifier(token, accessSecret).getExpiresAt());
    }

    public boolean validatorRefreshToken(String token) {
        return !Date.from(now.toInstant()).equals(getVerifier(token, refreshSecret).getExpiresAt());
    }


    private DecodedJWT getVerifier(String token, String secret) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withSubject("user")
                .withIssuer("Server")
                .build()
                .verify(token);
    }
    public String getUUID(String token) {
        return getVerifier(token, refreshSecret).getClaim("id").asString();
    }

    public String getUsername(String token) {
        return getVerifier(token, accessSecret).getClaim("email").asString();
    }

    public Authentication getAuthentication(String token) {
        String email = getUsername(token);

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
    }

    private String getRole(Role role) {
        return role.name();
    }
}
