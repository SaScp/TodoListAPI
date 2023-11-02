package ru.alex.task_managemen_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.alex.task_managemen_system.model.dto.task.RegistrationTaskDTO;
import ru.alex.task_managemen_system.model.dto.task.TaskDTO;
import ru.alex.task_managemen_system.model.task.Status;
import ru.alex.task_managemen_system.model.task.Task;
import ru.alex.task_managemen_system.repository.TaskRepository;
import ru.alex.task_managemen_system.repository.UserRepository;
import ru.alex.task_managemen_system.util.exception.UserNotFoundException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultTaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public List<TaskDTO> findAll(String id) {
        return taskRepository.findAllByUser_Uuid(id).orElseThrow(UserNotFoundException::new).stream().map(this::convertTaskToTaskDto).collect(Collectors.toList());
    }

    public TaskDTO save(RegistrationTaskDTO taskDTO, String id) {
        Task task = convertRegistrationTaskDtoToTask(taskDTO);

        task.setUuid(UUID.randomUUID().toString());

        task.setCreateAt(ZonedDateTime.now());
        task.setExpirationDate(ZonedDateTime.now().plusDays(10));

        task.setUser(userRepository.findById(id).orElseThrow(UserNotFoundException::new));

        task.setStatus(Status.IN_PROGRESS);

        taskRepository.save(task);
        return convertTaskToTaskDto(task);
    }

    private Task convertRegistrationTaskDtoToTask(RegistrationTaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }
    private TaskDTO convertTaskToTaskDto(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }

}
