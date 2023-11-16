package ru.alex.task_managemen_system.service.logger;

public interface SenderLog {
    void execute(String msg, boolean isError);
}
