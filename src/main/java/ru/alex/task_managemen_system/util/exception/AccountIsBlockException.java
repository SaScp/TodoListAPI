package ru.alex.task_managemen_system.util.exception;

public class AccountIsBlockException extends RuntimeException {

    public AccountIsBlockException(String msg) {
        super(msg);
    }
}
