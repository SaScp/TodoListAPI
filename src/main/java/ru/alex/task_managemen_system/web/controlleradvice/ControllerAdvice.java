package ru.alex.task_managemen_system.web.controlleradvice;

import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.alex.task_managemen_system.model.response.ErrorResponse;
import ru.alex.task_managemen_system.util.exception.*;
import ru.alex.task_managemen_system.util.exception.ResourceNotFoundException;
import ru.alex.task_managemen_system.util.exception.handler.ExceptionHandlerStrategy;
import ru.alex.task_managemen_system.util.exception.handler.impl.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {
    Map<Class<? extends RuntimeException>, ExceptionHandlerStrategy> handlers;

    public ControllerAdvice() {
        handlers = new HashMap<>();
        handlers.put(TasksNotFoundException.class, new TaskFoundExceptionHandler());
        handlers.put(UserNotFoundException.class, new UserNotFoundExceptionHandler());
        handlers.put(ResourceNotFoundException.class, new RegistrationUserExceptionHandler());
        handlers.put(AccountIsBlockException.class, new AccountDeniedExceptionHandler());
        handlers.put(PasswordEncoderException.class, new PasswordEncodedExceptionHandler());
        handlers.put(SendToEmailException.class, new SendToEmailExceptionHandler());
        handlers.put(RegistrationUserException.class, new RegistrationUserExceptionHandler());
    }
    @ExceptionHandler(
            {
                    TasksNotFoundException.class,
                    AccessDeniedException.class,
                    UserNotFoundException.class,
                    PasswordEncoderException.class,
                    AccountIsBlockException.class,
                    RegistrationUserException.class,
                    ResourceNotFoundException.class,
                    SendToEmailException.class,
            }
    )
    private ResponseEntity<ErrorResponse> exHandler(RuntimeException e) {
        ExceptionHandlerStrategy strategy = handlers.get(e.getClass());
        return ResponseEntity
                .badRequest()
                .body(strategy.handleException(e));
    }
}
