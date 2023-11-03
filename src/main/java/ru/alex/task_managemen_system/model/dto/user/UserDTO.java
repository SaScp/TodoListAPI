package ru.alex.task_managemen_system.model.dto.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {


    private String name;

    @Email
    @NotNull(message = "the email must not be null")

    private String email;

    @NotNull(message = "the password must not be null")
    @Size(min = 7, max = 32, message = "The password must be more than 8 characters")
    private String password;


}
