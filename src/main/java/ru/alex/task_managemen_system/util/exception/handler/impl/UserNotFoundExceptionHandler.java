package ru.alex.task_managemen_system.util.exception.handler.impl;

import ru.alex.task_managemen_system.model.response.ErrorResponse;
import ru.alex.task_managemen_system.util.exception.handler.ExceptionHandlerStrategy;

import java.time.ZonedDateTime;

public class UserNotFoundExceptionHandler implements ExceptionHandlerStrategy {
    @Override
    public ErrorResponse handleException(RuntimeException exception) {
        return new ErrorResponse("User not found", ZonedDateTime.now(), 401);
    }
}
