package ru.alex.task_managemen_system.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import ru.alex.task_managemen_system.model.task.Task;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "t_user")
public class User {

    @Id
    private String uuid;

    @Column(name = "c_name")
    private String name;

    @Column(name = "email", nullable = false)
    @Email
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "c_role", nullable = false)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    @Column(name = "create_at", nullable = false)
    private ZonedDateTime createAt;

    @Column(name = "update_at", nullable = false)
    private ZonedDateTime updateAt;
}
