package ru.alex.task_managemen_system.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.task_managemen_system.model.dto.task.RegistrationTaskDTO;
import ru.alex.task_managemen_system.model.dto.task.TaskDTO;
import ru.alex.task_managemen_system.service.TaskService;
import ru.alex.task_managemen_system.service.impl.DefaultTaskService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/task/")
public class TaskController {

    @Qualifier("defaultTaskService")
    private final TaskService taskService;

    @GetMapping("/all-task/{userId}")
    public ResponseEntity<List<TaskDTO>> findAllTasks(@PathVariable("userId") String id) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok().body(taskService.findAll(id).get());
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<TaskDTO> add(@RequestBody @Valid RegistrationTaskDTO taskDTO,
                                       @PathVariable("userId") String userId) throws ExecutionException, InterruptedException {
        TaskDTO task = taskService.save(taskDTO, userId).get();
        return ResponseEntity.ok().body(task);
    }

    /*@PatchMapping("/update/{id}")
    public ResponseEntity<TaskDTO> update(@PathVariable("id") String id,
                                          @RequestBody @Valid TaskDTO taskDTO) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok().body(taskService.update(id, taskDTO).get());
    }*/ //TODO
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<TaskDTO> delete(@PathVariable("taskId") String id,
                                          @RequestHeader("Authorization") String token) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok().body(taskService.delete(token, id).get());
    }
}
