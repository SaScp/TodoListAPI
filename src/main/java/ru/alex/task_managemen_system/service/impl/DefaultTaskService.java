package ru.alex.task_managemen_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.alex.task_managemen_system.model.dto.task.RegistrationTaskDTO;
import ru.alex.task_managemen_system.model.dto.task.TaskDTO;
import ru.alex.task_managemen_system.model.task.Status;
import ru.alex.task_managemen_system.model.task.Task;
import ru.alex.task_managemen_system.repository.TaskRepository;
import ru.alex.task_managemen_system.repository.UserRepository;
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

@Service
@RequiredArgsConstructor
public class DefaultTaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final DefaultJwtService jwtService;

    @Async
    public CompletableFuture<List<TaskDTO>> findAll(String token) {
        String id = getIdFromToken(token);
        List<TaskDTO> taskDTOs = taskRepository
                .findAllByUser_Uuid(id)
                .orElseThrow(UserNotFoundException::new)
                .stream().map(this::convertTaskToTaskDto)
                .toList();
        return CompletableFuture.completedFuture(taskDTOs);
    }

    @Async
    public CompletableFuture<TaskDTO> save(RegistrationTaskDTO taskDTO,
                                           String token) {
        String id = getIdFromToken(token);

        Task task = convertRegistrationTaskDtoToTask(taskDTO);

        task.setUuid(UUID.randomUUID().toString());

        task.setCreateAt(ZonedDateTime.now());
        task.setExpirationDate(ZonedDateTime.now().plusDays(10));

        task.setUser(userRepository.findById(id).orElseThrow(UserNotFoundException::new));

        task.setStatus(Status.IN_PROGRESS);

        taskRepository.save(task);
        return CompletableFuture.completedFuture(convertTaskToTaskDto(task));
    }

    @Async
    public CompletableFuture<TaskDTO> update(String taskId,
                                             TaskDTO taskDTO) {


        Task task = taskRepository.findTaskByUuid(taskId).orElseThrow(TasksNotFoundException::new);

        List<UpdateComponent> updateComponents = List.of(new UpdateDescription(), new UpdateStatus(), new UpdateTitle());

        for (var i : updateComponents) {
            i.execute(taskDTO, task);
        }

        return CompletableFuture.completedFuture(convertTaskToTaskDto(task));
    }
    @Async
    public CompletableFuture<TaskDTO> delete(String token,
                                             String taskId) {
        String id = getIdFromToken(token);

        Task taskToBeDeleted = taskRepository.deleteTaskByUuidAndUser_Uuid(taskId, token).orElseThrow(TasksNotFoundException::new);

        return CompletableFuture.completedFuture(convertTaskToTaskDto(taskToBeDeleted));
    }

    public String getIdFromToken(String token) {
        return jwtService.getAccessUUID(token.substring(7));
    }

    private Task convertRegistrationTaskDtoToTask(RegistrationTaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }

    private TaskDTO convertTaskToTaskDto(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }

}
