package ru.alex.task_managemen_system.model.dto;

import lombok.Data;
import ru.alex.task_managemen_system.model.user.Role;

import java.util.Set;

@Data
public class Token {
    private String uuid;
    private String email;
    private Set<Role> roles;
}
