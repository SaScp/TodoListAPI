package ru.alex.task_managemen_system.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.alex.task_managemen_system.model.response.JwtResponse;
import ru.alex.task_managemen_system.service.impl.DefaultJwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final DefaultJwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && !bearerToken.isEmpty()  && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
            if (!bearerToken.isBlank() && jwtService.validatorAccessToken(bearerToken)) {
                try {
                    Authentication authentication = jwtService.getAuthentication(bearerToken);
                    if (authentication != null) {
                        SecurityContextHolder.getContext().setAuthentication(jwtService.getAuthentication(bearerToken));
                    }
                } catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

        }
        filterChain.doFilter(request, response);
    }


}
