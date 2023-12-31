package ru.alex.task_managemen_system.model.dto.task;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class RegistrationTaskDTO {

    @NotNull
    private String title;

    @NotNull
    private String description;

    private ZonedDateTime expirationDate;
}
