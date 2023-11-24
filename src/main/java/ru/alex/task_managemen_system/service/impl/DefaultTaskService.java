package ru.alex.task_managemen_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.alex.task_managemen_system.model.dto.img.ImageDTO;

import ru.alex.task_managemen_system.model.dto.task.TaskDTO;
import ru.alex.task_managemen_system.model.task.Image;
import ru.alex.task_managemen_system.model.task.Status;
import ru.alex.task_managemen_system.model.task.Task;
import ru.alex.task_managemen_system.repository.ImageRepository;
import ru.alex.task_managemen_system.repository.TaskRepository;
import ru.alex.task_managemen_system.repository.UserRepository;
import ru.alex.task_managemen_system.service.TaskService;
import ru.alex.task_managemen_system.service.logger.DefaultSenderLog;
import ru.alex.task_managemen_system.service.update.update_task.UpdateComponent;
import ru.alex.task_managemen_system.service.update.update_task.UpdateDescription;
import ru.alex.task_managemen_system.service.update.update_task.UpdateStatus;
import ru.alex.task_managemen_system.service.update.update_task.UpdateTitle;
import ru.alex.task_managemen_system.util.exception.TasksNotFoundException;
import ru.alex.task_managemen_system.util.exception.UserNotFoundException;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultTaskService implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final DefaultJwtService jwtService;
    private final DefaultSenderLog senderLogger;
    private final ImageRepository imageRepository;

    @Async
    public CompletableFuture<List<TaskDTO>> findAll(String id) {
        List<Task> tasks = taskRepository
                .findAllByUser_Uuid(id)
                .orElseThrow(UserNotFoundException::new);
        senderLogger.execute(this.getClass().getName() + " : " +
                "FindAllTask", false);

        List<TaskDTO> taskDTOs = tasks.stream().map(this::convertTaskToTaskDto).toList();

        for (int i = 0; i < taskDTOs.size(); i++) {
            taskDTOs.get(i).setImage(tasks.get(i)
                    .getImages().stream()
                    .map(this::convertImageToImageDto)
                    .toList());
        }

        return CompletableFuture.completedFuture(taskDTOs);
    }

    @Async
    public CompletableFuture<TaskDTO> save(TaskDTO taskDTO,
                                           String userId) {

      Task task = convertTaskDtoToTask(taskDTO);

        task.setUuid(UUID.randomUUID().toString());

        task.setCreateAt(ZonedDateTime.now());
        task.setExpirationDate(ZonedDateTime.now().plusDays(10));

        task.setUser(userRepository.findById(userId).orElseThrow(UserNotFoundException::new));

        task.setStatus(Status.IN_PROGRESS);

        taskRepository.save(task);
        senderLogger.execute(this.getClass().getName() + " : " +
                "save task: " + task.getUuid(), false);
        return CompletableFuture.completedFuture(convertTaskToTaskDto(task));
    }

    public Boolean addImg(String id, ImageDTO file) {
       try {
           Task task = taskRepository.findTaskByUuid(id).orElseThrow(TasksNotFoundException::new);
           Image image = new Image();
           image.setTask(task);
           image.setImg(file.getImg());
           image.setUuid(UUID.randomUUID().toString());
           task.add(image);


           taskRepository.save(task);
           imageRepository.save(image);

           return true;
       }catch (Exception e) {
           return false;
       }
    }
    public Boolean deleteImg(String id) {
            imageRepository.deleteById(id);
            return true;
    }

    @Async
    public CompletableFuture<TaskDTO> update(String taskId,
                                             TaskDTO taskDTO) {

        Task task = taskRepository.findTaskByUuid(taskId).orElseThrow(TasksNotFoundException::new);

        List<UpdateComponent> updateComponents = List.of(new UpdateDescription(), new UpdateStatus(), new UpdateTitle());

        for (var i : updateComponents) {
            i.execute(taskDTO, task);
        }
        senderLogger.execute(
                this.getClass().getName() + " : " +
                        "update task: " + task.getUuid(), false);
        return CompletableFuture.completedFuture(convertTaskToTaskDto(task));
    }

    @Async
    public CompletableFuture<TaskDTO> delete(String taskId) {
        Task taskToBeDeleted = taskRepository
                .deleteTaskByUuid(taskId)
                .orElseThrow(TasksNotFoundException::new);

        senderLogger.execute(
                this.getClass().getName() + " : " +
                        "delete task: " + taskToBeDeleted.getUuid(), false);

        return CompletableFuture.completedFuture(convertTaskToTaskDto(taskToBeDeleted));
    }

    private Task convertTaskDtoToTask(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO,Task.class);
    }

    private TaskDTO convertTaskToTaskDto(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }

    private ImageDTO convertImageToImageDto(Image image) {
        return modelMapper.map(image, ImageDTO.class);
    }

}
