package ru.alex.task_managemen_system.model.dto.task;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import ru.alex.task_managemen_system.model.dto.img.ImageDTO;
import ru.alex.task_managemen_system.model.task.Image;
import ru.alex.task_managemen_system.model.task.Status;


import java.time.ZonedDateTime;
import java.util.List;

@Data
public class TaskDTO {

    private String uuid;

    private String title;

    private String description;

    private ZonedDateTime expirationDate;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    private List<ImageDTO> image;
}
