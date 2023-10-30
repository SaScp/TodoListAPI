package ru.alex.task_managemen_system.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.alex.task_managemen_system.model.task.Task;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @Column(name = "uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Column(name = "c_name")
    private String name;

    @Column(name = "email", unique = true)
    @Email
    @NotNull(message = "the email must not be null")
    private String email;

    @Column(name = "password")
    @NotNull(message = "the password must not be null")
    private String password;


    @Column(name = "c_role")
    @Enumerated(value = EnumType.STRING)
    private Role roles;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    @Column(name = "create_at", nullable = false)
    private ZonedDateTime createAt;

    @Column(name = "update_at", nullable = false)
    private ZonedDateTime updateAt;
}
