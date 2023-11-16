package ru.alex.task_managemen_system.service.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DefaultSenderLog implements SenderLog {

    public void execute(String msg, boolean isError) {
            if (isError) {
                log.error(msg);
            } else {
                log.info(msg);
            }
    }
}
