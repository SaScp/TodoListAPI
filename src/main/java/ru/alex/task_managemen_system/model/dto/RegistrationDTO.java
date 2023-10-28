package ru.alex.task_managemen_system.model.dto;

import lombok.Data;

@Data
public class RegistrationDTO {

    private String name;
    private String password;
    private String email;

}
