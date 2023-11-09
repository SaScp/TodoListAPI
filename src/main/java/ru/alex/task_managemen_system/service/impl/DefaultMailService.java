package ru.alex.task_managemen_system.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.mail.SimpleMailMessageItemWriter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ru.alex.task_managemen_system.service.MailService;

import java.io.FileNotFoundException;

@Service
@RequiredArgsConstructor
public class DefaultMailService implements MailService {

    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(DefaultMailService.class);
    public void send(String toAddress, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText(message);
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        mailSender.send(simpleMailMessage);


    }

}
