package ru.alex.task_managemen_system.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDTO {

    @Email
    @NotNull(message = "the email must not be null")
    private String email;

    @NotNull(message = "the password must not be null")
    private String password;
}
