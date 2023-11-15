package ru.alex.task_managemen_system.service.logger;

public interface Sender {
    void execute(String msg, boolean isError);
}
