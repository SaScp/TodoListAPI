package ru.alex.task_managemen_system.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.task_managemen_system.model.dto.RegistrationTaskDTO;
import ru.alex.task_managemen_system.model.task.Task;
import ru.alex.task_managemen_system.service.impl.DefaultTaskService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/tasks/")
public class TaskController {

    private final DefaultTaskService taskService;

    @GetMapping("/all-task/{id}")
    public ResponseEntity<List<Task>> findAllTasks(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(taskService.findAll(id));
    }

    @PostMapping("/add/{user-id}")
    public ResponseEntity<Task> add(@RequestBody @Valid RegistrationTaskDTO taskDTO, @PathVariable("user-id") String id) {
        return ResponseEntity.ok().body(taskService.save(taskDTO, id));
    }
}
