package ru.alex.task_managemen_system.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.alex.task_managemen_system.security.auth.DefaultAuthenticationProvider;
import ru.alex.task_managemen_system.security.auth.DefaultUserDetailsService;
import ru.alex.task_managemen_system.service.logger.DefaultSenderLog;

@Configuration
@RequiredArgsConstructor
public class ProviderConfiguration {

    private final DefaultUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final DefaultSenderLog senderLogger;
    @Bean
    public DefaultAuthenticationProvider authProvider() {
        return new DefaultAuthenticationProvider(userDetailsService, passwordEncoder, senderLogger);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
