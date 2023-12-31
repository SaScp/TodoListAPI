package ru.alex.task_managemen_system.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.alex.task_managemen_system.service.logger.DefaultSenderLog;
import ru.alex.task_managemen_system.util.exception.AccountIsBlockException;
import ru.alex.task_managemen_system.util.exception.PasswordEncoderException;

@Component
@RequiredArgsConstructor
public class DefaultAuthenticationProvider implements AuthenticationProvider {

    private final DefaultUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final DefaultSenderLog senderLogger;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();

        DefaultUserDetails userDetails = (DefaultUserDetails) userDetailsService.loadUserByUsername(name);

        if (!passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
            senderLogger.execute("Password invalid", true);
            throw new PasswordEncoderException();
        }

        if (userDetails.isAccountNonLocked()) {
            senderLogger.execute("Account: " + authentication.getName() + "have block", true);
            throw new AccountIsBlockException(
                    String.format("Account with email %s is block", userDetails.getUsername()));
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
