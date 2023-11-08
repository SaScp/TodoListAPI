package ru.alex.task_managemen_system.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.alex.task_managemen_system.security.auth.DefaultAuthenticationProvider;
import ru.alex.task_managemen_system.security.auth.DefaultUserDetailsService;

@Configuration
@RequiredArgsConstructor
public class ProviderConfiguration {

    private final DefaultUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    @Bean
    public DefaultAuthenticationProvider authProvider() {
        return new DefaultAuthenticationProvider(userDetailsService, passwordEncoder);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
