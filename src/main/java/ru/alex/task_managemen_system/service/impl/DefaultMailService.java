package ru.alex.task_managemen_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.alex.task_managemen_system.service.MailService;
import ru.alex.task_managemen_system.service.logger.DefaultSenderLog;
import ru.alex.task_managemen_system.util.exception.SendToEmailException;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultMailService implements MailService {

    private final JavaMailSender mailSender;
    private final DefaultSenderLog senderLogger;

    public void send(String toAddress, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText(message);
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        try {
            mailSender.send(simpleMailMessage);

            senderLogger.execute(
                    this.getClass().getName() + " : " +
                            "message send to email: " + toAddress, false);
        } catch (SendToEmailException e) {

            senderLogger.execute(
                    this.getClass().getName() + " : " +
                            "message not send to email: " + toAddress + " because \n" + e.getMessage(), true);
        }

    }

}
