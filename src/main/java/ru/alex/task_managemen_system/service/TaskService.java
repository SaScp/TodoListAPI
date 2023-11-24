package ru.alex.task_managemen_system.service;

import ru.alex.task_managemen_system.model.dto.img.ImageDTO;
import ru.alex.task_managemen_system.model.dto.task.TaskDTO;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TaskService {


    CompletableFuture<List<TaskDTO>> findAll(String token);

    CompletableFuture<TaskDTO> save(TaskDTO taskDTO, String token);

    CompletableFuture<TaskDTO> update(String taskId, TaskDTO taskDTO);

    CompletableFuture<TaskDTO> delete(String taskId);

   Boolean addImg(String id, ImageDTO file);

    Boolean deleteImg(String id);
}