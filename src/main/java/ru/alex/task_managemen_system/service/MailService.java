package ru.alex.task_managemen_system.service;

import jakarta.mail.MessagingException;

import java.io.FileNotFoundException;

public interface MailService {
    public void send(String toAddress, String subject, String message);
}
