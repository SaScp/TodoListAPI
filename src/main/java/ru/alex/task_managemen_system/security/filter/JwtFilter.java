package ru.alex.task_managemen_system.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.alex.task_managemen_system.model.dto.UserDTO;
import ru.alex.task_managemen_system.model.user.Role;
import ru.alex.task_managemen_system.service.JwtService;
import ru.alex.task_managemen_system.service.UserService;
import ru.alex.task_managemen_system.service.impl.DefaultJwtService;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final DefaultJwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authentication");

        if (!bearerToken.isEmpty() && bearerToken != null && bearerToken.startsWith("Bearer ")) {

            bearerToken = bearerToken.substring(7);

            if (bearerToken.isBlank() || jwtService.validator(bearerToken)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            } else {
                try {
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(jwtService.getAuthentication(bearerToken));
                    }
                } catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            }

        }
    }


}
