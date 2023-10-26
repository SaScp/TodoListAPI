package ru.alex.task_managemen_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex.task_managemen_system.model.user.User;

public interface UseRepository extends JpaRepository<User, String> {
}
