package ru.alex.task_managemen_system.model.task;

import lombok.Data;
import ru.alex.task_managemen_system.model.user.Role;
import ru.alex.task_managemen_system.model.user.User;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Data
public class Task {
    private String uuid;

    private String title;

    private String description;

    private Set<Status> status;

    private User user;

    private ZonedDateTime create_at;

    private ZonedDateTime expiration_date;
}
