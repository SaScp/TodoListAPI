package ru.alex.task_managemen_system.model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.alex.task_managemen_system.model.task.Status;

import java.time.ZonedDateTime;

@Data
public class TaskDTO {

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private ZonedDateTime expirationDate;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Status status;

}
