package ru.alex.task_managemen_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alex.task_managemen_system.model.task.Task;
import ru.alex.task_managemen_system.model.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    Optional<List<Task>> findByUser_Uuid(String uuid);
}
