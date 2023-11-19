package ru.alex.task_managemen_system.util.exception.handler.impl;

import ru.alex.task_managemen_system.model.response.ErrorResponse;
import ru.alex.task_managemen_system.util.exception.handler.ExceptionHandlerStrategy;

import java.time.ZonedDateTime;

public class ResourceNotFoundException implements ExceptionHandlerStrategy {
    @Override
    public ErrorResponse handleException(RuntimeException exception) {
        return new ErrorResponse("Resource Not Found", ZonedDateTime.now(), 401);
    }
}
