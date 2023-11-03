package ru.alex.task_managemen_system.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.task_managemen_system.model.dto.task.RegistrationTaskDTO;
import ru.alex.task_managemen_system.model.dto.task.TaskDTO;
import ru.alex.task_managemen_system.service.impl.DefaultTaskService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/task/")
public class TaskController {

    private final DefaultTaskService taskService;

    @GetMapping("/all-task")
    public ResponseEntity<List<TaskDTO>> findAllTasks(@RequestHeader("Authorization") String token) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok().body(taskService.findAll(token).get());
    }

    @PostMapping("/add")
    public ResponseEntity<TaskDTO> add(@RequestBody @Valid RegistrationTaskDTO taskDTO,
                                       @RequestHeader("Authorization") String token) throws ExecutionException, InterruptedException {
        TaskDTO task = taskService.save(taskDTO, token).get();
        return ResponseEntity.ok().body(task);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<TaskDTO> update(@PathVariable("id") String id, @RequestBody @Valid TaskDTO taskDTO) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok().body(taskService.update(id, taskDTO).get());
    }
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<TaskDTO> delete(@PathVariable("taskId") String id,
                                          @RequestHeader("Authorization") String token) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok().body(taskService.delete(token, id).get());
    }
}
