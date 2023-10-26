package ru.alex.task_managemen_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex.task_managemen_system.model.task.Task;

public interface TaskRepository extends JpaRepository<Task, String> {
}
