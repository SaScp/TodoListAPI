package ru.alex.task_managemen_system.service;

import org.springframework.scheduling.annotation.Async;
import ru.alex.task_managemen_system.model.dto.task.RegistrationTaskDTO;
import ru.alex.task_managemen_system.model.dto.task.TaskDTO;
import ru.alex.task_managemen_system.model.task.Status;
import ru.alex.task_managemen_system.model.task.Task;
import ru.alex.task_managemen_system.service.update.update_task.UpdateComponent;
import ru.alex.task_managemen_system.service.update.update_task.UpdateDescription;
import ru.alex.task_managemen_system.service.update.update_task.UpdateStatus;
import ru.alex.task_managemen_system.service.update.update_task.UpdateTitle;
import ru.alex.task_managemen_system.util.exception.TasksNotFoundException;
import ru.alex.task_managemen_system.util.exception.UserNotFoundException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface TaskService {


    CompletableFuture<List<TaskDTO>> findAll(String token);

    CompletableFuture<TaskDTO> save(RegistrationTaskDTO taskDTO, String token);


    CompletableFuture<TaskDTO> update(String taskId, TaskDTO taskDTO);

    CompletableFuture<TaskDTO> delete(String token, String taskId);
}
