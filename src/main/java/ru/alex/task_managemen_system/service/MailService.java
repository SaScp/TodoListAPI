package ru.alex.task_managemen_system.service;

public interface MailService {
    void send(String toAddress, String subject, String message);
}
