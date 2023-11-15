package ru.alex.task_managemen_system.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.alex.task_managemen_system.model.response.JwtResponse;
import ru.alex.task_managemen_system.service.impl.DefaultJwtService;
import ru.alex.task_managemen_system.service.logger.DefaultSenderLogger;

import java.io.IOException;
import java.time.ZonedDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final DefaultJwtService jwtService;
    private final DefaultSenderLogger senderLogger;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && !bearerToken.isEmpty() && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
            if (!bearerToken.isBlank() && jwtService.validatorAccessToken(bearerToken)) {

                senderLogger.execute(ZonedDateTime.now() + " : " +
                        this.getClass().getName() + " : " +
                        "bearer token exists", false);

                try {
                    Authentication authentication = jwtService.getAuthentication(bearerToken);
                    if (authentication != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        senderLogger.execute(ZonedDateTime.now() + " : " +
                                this.getClass().getName() + " : " +
                                "SecurityContextHolder is create", false);
                    }
                } catch (Exception e) {

                    senderLogger.execute(ZonedDateTime.now() + " : " +
                            this.getClass().getName() + " : " +
                            "USER_FORBIDDEN", true);
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            } else {
                senderLogger.execute(ZonedDateTime.now() + " : " +
                        this.getClass().getName() + " : " +
                        "USER_UNAUTHORIZED", true);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

        }
        filterChain.doFilter(request, response);
    }

}
