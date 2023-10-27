package ru.alex.task_managemen_system.model.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class UpdateDTO {

    private String name;
    private String password;
    private String email;
}
