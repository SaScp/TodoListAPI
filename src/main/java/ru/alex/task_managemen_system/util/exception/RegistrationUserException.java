package ru.alex.task_managemen_system.util.exception;

public class RegistrationUserException extends RuntimeException {
    public RegistrationUserException(String defaultMessage) {
        super(defaultMessage);
    }
}
