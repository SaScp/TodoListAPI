package ru.alex.task_managemen_system.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import ru.alex.task_managemen_system.security.AuthProvider;
import ru.alex.task_managemen_system.security.UserDetailsServiceImpl;

@Configuration
@RequiredArgsConstructor
public class ProviderConfiguration {

    private final UserDetailsServiceImpl userDetailsService;
    @Bean
    public AuthProvider authProvider() {
        return new AuthProvider();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
