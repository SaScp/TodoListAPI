package ru.alex.task_managemen_system.model.task;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CollectionType;
import ru.alex.task_managemen_system.model.user.Role;
import ru.alex.task_managemen_system.model.user.User;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "t_task")
public class Task implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "uuid")
    private User user;

    @Column(name = "create_at", nullable = false)
    private ZonedDateTime createAt;

    @Column(name = "expiration_date", nullable = false)
    private ZonedDateTime expirationDate;
}
