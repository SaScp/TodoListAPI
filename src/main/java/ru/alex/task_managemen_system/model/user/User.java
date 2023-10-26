package ru.alex.task_managemen_system.model.user;

import lombok.Data;
import ru.alex.task_managemen_system.model.task.Task;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Data
public class User {

    private String uuid;

    private String c_name;

    private String email;

    private Set<Role> roles;

    private List<Task> tasks;

    private ZonedDateTime create_at;

    private ZonedDateTime update_at;
}
