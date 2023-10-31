package ru.alex.task_managemen_system.web.controlleradvice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.alex.task_managemen_system.model.response.ErrorResponse;
import ru.alex.task_managemen_system.util.exception.AccessDeniedException;
import ru.alex.task_managemen_system.util.exception.PasswordEncoderException;
import ru.alex.task_managemen_system.util.exception.TasksNotFoundException;
import ru.alex.task_managemen_system.util.exception.UserNotFoundException;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({AccessDeniedException.class})
    private ResponseEntity<ErrorResponse> exHandler(AccessDeniedException exception) {
        ErrorResponse errorResponse =
                new ErrorResponse(exception.getMessage(), ZonedDateTime.now(), 401);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler({UserNotFoundException.class})
    private ResponseEntity<ErrorResponse> exHandler(UserNotFoundException exception) {
        ErrorResponse errorResponse =
                new ErrorResponse("User not found", ZonedDateTime.now(), 401);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler({TasksNotFoundException.class})
    private ResponseEntity<ErrorResponse> exHandler(TasksNotFoundException exception) {
        ErrorResponse errorResponse =
                new ErrorResponse("Task not found", ZonedDateTime.now(), 401);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler({PasswordEncoderException.class})
    private ResponseEntity<ErrorResponse> exHandler(PasswordEncoderException exception) {
        ErrorResponse errorResponse =
                new ErrorResponse("Password a is invalid", ZonedDateTime.now(), 401);

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
