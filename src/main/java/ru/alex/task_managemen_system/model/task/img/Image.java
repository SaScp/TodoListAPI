package ru.alex.task_managemen_system.model.task.img;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.alex.task_managemen_system.model.task.Task;

@Entity
@Table(name = "t_img_task")
@Getter
@Setter
public class Image {

    @Id
    @Column(name = "img_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Column(name = "img")
    private String img;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id", referencedColumnName = "uuid")
    private Task task;
}
