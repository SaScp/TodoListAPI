package ru.alex.task_managemen_system.util.exception;

public class SendToEmailException extends RuntimeException {

    public SendToEmailException(String msg) {
        super(msg);
    }
}
