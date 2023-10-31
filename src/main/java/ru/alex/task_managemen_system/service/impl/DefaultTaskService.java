package ru.alex.task_managemen_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.alex.task_managemen_system.model.dto.RegistrationTaskDTO;
import ru.alex.task_managemen_system.model.task.Status;
import ru.alex.task_managemen_system.model.task.Task;
import ru.alex.task_managemen_system.repository.TaskRepository;
import ru.alex.task_managemen_system.repository.UserRepository;
import ru.alex.task_managemen_system.util.exception.UserNotFoundException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultTaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public List<Task> findAll(String id) {
        return taskRepository.findByUser_Uuid(id);
    }

    public Task save(RegistrationTaskDTO taskDTO, String id) {
        Task task = convertTaskDtoToTask(taskDTO);

        task.setUuid(UUID.randomUUID().toString());

        task.setCreateAt(ZonedDateTime.now());
        task.setExpirationDate(ZonedDateTime.now().plusDays(10));

        task.setUser(userRepository.findById(id).orElseThrow(UserNotFoundException::new));

        task.setStatus(Status.IN_PROGRESS);

        taskRepository.save(task);
        return task;
    }

    private Task convertTaskDtoToTask(RegistrationTaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }
}
