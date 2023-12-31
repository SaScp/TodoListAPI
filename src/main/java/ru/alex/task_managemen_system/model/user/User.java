package ru.alex.task_managemen_system.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.alex.task_managemen_system.model.task.Task;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "t_user")
public class User implements Serializable {

    @Id
    @Column(name = "uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Column(name = "c_name")
    private String name;

    @Column(name = "email", unique = true)
    @NotNull(message = "the email must not be null")
    private String email;

    @Column(name = "password")
    @NotNull(message = "the password must not be null")
    private String password;


    @Column(name = "c_role")
    @Enumerated(value = EnumType.STRING)
    private Role roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Task> tasks;

    @Column(name = "create_at", nullable = false)
    private ZonedDateTime createAt;

    @Column(name = "update_at", nullable = false)
    private ZonedDateTime updateAt;
}
