package ru.alex.task_managemen_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alex.task_managemen_system.model.task.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
}
