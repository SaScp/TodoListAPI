package ru.alex.task_managemen_system.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import ru.alex.task_managemen_system.service.logger.DefaultSenderLogger;
import ru.alex.task_managemen_system.util.exception.SendToEmailException;

import java.io.FileNotFoundException;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultMailService implements MailService {

    private final JavaMailSender mailSender;
    private final DefaultSenderLogger senderLogger;
    public void send(String toAddress, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText(message);
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        try {
            mailSender.send(simpleMailMessage);

            senderLogger.execute(ZonedDateTime.now() + " : " +
                    this.getClass().getName() + " : " +
                    "message send to email: " + toAddress, false);
        } catch (SendToEmailException e) {

            senderLogger.execute(ZonedDateTime.now() + " : " +
                    this.getClass().getName() + " : " +
                    "message not send to email: " + toAddress + " because \n" + e.getMessage(), true);
        }

    }

}
