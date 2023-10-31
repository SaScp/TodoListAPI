package ru.alex.task_managemen_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alex.task_managemen_system.model.task.Task;
import ru.alex.task_managemen_system.model.user.User;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    List<Task> findByUser_Uuid(String uuid);
}
