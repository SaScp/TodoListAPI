package ru.alex.task_managemen_system.util.exception.handler;

import ru.alex.task_managemen_system.model.response.ErrorResponse;

public interface ExceptionHandlerStrategy {
    ErrorResponse handleException(RuntimeException exception);
}
