package ru.alex.task_managemen_system.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.task_managemen_system.model.dto.user.UpdateDTO;
import ru.alex.task_managemen_system.model.user.User;
import ru.alex.task_managemen_system.service.UserService;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    @Qualifier("defaultUserService")
    private final UserService userService;
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") String id) {

        return ResponseEntity
                .ok()
                .body(userService.getUserByUUID(id));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<User> update(@PathVariable("id") String id, @RequestBody UpdateDTO updateDTO) throws ExecutionException, InterruptedException {
        return ResponseEntity
                .ok()
                .body(userService.update(updateDTO, id));
    }

}
