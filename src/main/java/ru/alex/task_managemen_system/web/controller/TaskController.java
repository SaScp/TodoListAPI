package ru.alex.task_managemen_system.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.task_managemen_system.model.dto.RegistrationTaskDTO;
import ru.alex.task_managemen_system.model.dto.TaskDTO;
import ru.alex.task_managemen_system.model.task.Task;
import ru.alex.task_managemen_system.service.impl.DefaultTaskService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/task/")
public class TaskController {

    private final DefaultTaskService taskService;

    @GetMapping("/all-task/{id}")
    public List<TaskDTO> findAllTasks(@PathVariable("id") String id) {
        List<TaskDTO> tasks = taskService.findAll(id);
        return tasks;
    }

    @PostMapping("/add/{user-id}")
    public TaskDTO add(@RequestBody @Valid RegistrationTaskDTO taskDTO, @PathVariable("user-id") String id) {
        TaskDTO task = taskService.save(taskDTO, id);
        return task;
    }
}
