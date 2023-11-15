package ru.alex.task_managemen_system.model.dto.task;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.alex.task_managemen_system.model.task.Status;

import java.time.ZonedDateTime;

@Data
public class TaskDTO {

    private String title;

    private String description;

    private ZonedDateTime expirationDate;

    @Enumerated(value = EnumType.STRING)
    private Status status;

}
