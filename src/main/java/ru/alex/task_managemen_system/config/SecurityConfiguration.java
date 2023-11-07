package ru.alex.task_managemen_system.config;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.AbstractSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.alex.task_managemen_system.security.filter.JwtFilter;

import java.nio.file.AccessDeniedException;
import java.util.Date;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {


    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.exceptionHandling(exceptionHandlingConfigurer ->
                exceptionHandlingConfigurer.authenticationEntryPoint((request, response, authException) -> {
                    response.getWriter().write("{error : 401, msg: UNAUTHORIZED}");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }).accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter().write("FORBIDDEN");
                        }
                )
        );
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests(this::getAuthHttpRequest);

        http.anonymous(AbstractHttpConfigurer::disable);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry getAuthHttpRequest(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authHttpRequest) {
        return authHttpRequest.requestMatchers("/v1/auth/**")
                .permitAll()
                .requestMatchers("/v1/admin/**")
                .hasAnyRole("ADMIN")
                .anyRequest()
                .authenticated();
    }
}
